package dk.mosberg.mam.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
/**
 * Custom spell projectile entity for the Mana and Magic mod.
 * Handles spell casting, damage calculation, and collision detection.
 *
 * Features:
 * - School-specific colors (Fire, Water, Earth, Air, Thunder, Light, Dark,
 * Nature, Ice, Arcane, Blood, Chaos)
 * - Damage scaling based on spell level and caster's mana
 * - Collision with entities and blocks
 * - Configurable projectile lifetime and travel distance
 * - Network synchronization for client-side effects
 *
 * @author Mosberg
 * @version 1.0.0
 */
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SpellProjectileEntity extends ProjectileEntity implements FlyingItemEntity {

  @Override
  public ItemStack getStack() {
    // Return a default item for rendering (e.g., stick or custom spell item)
    return new ItemStack(Items.STICK);
  }

  // Data tracking fields
  private static final TrackedData<Integer> SPELL_SCHOOL = DataTracker.registerData(
      SpellProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);

  private static final TrackedData<Float> DAMAGE = DataTracker.registerData(
      SpellProjectileEntity.class, TrackedDataHandlerRegistry.FLOAT);

  private static final TrackedData<Integer> LIFETIME_TICKS = DataTracker.registerData(
      SpellProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);

  private static final TrackedData<String> SPELL_NAME = DataTracker.registerData(
      SpellProjectileEntity.class, TrackedDataHandlerRegistry.STRING);

  // Spell school color codes (RGB)
  private static final int COLOR_FIRE = 0xFF4500; // Orange-Red
  private static final int COLOR_WATER = 0x4169E1; // Royal Blue
  private static final int COLOR_EARTH = 0x8B4513; // Saddle Brown
  private static final int COLOR_AIR = 0xB0E0E6; // Powder Blue
  private static final int COLOR_THUNDER = 0xFFD700; // Gold
  private static final int COLOR_LIGHT = 0xFFFACD; // Lemon Chiffon
  private static final int COLOR_DARK = 0x2F4F4F; // Dark Slate Gray
  private static final int COLOR_NATURE = 0x228B22; // Forest Green
  private static final int COLOR_ICE = 0x87CEEB; // Sky Blue
  private static final int COLOR_ARCANE = 0x9932CC; // Dark Orchid
  private static final int COLOR_BLOOD = 0x8B0000; // Dark Red
  private static final int COLOR_CHAOS = 0xFF1493; // Deep Pink

  // Entity properties
  private int ticksInAir = 0;
  private int maxTravelDistance = 256; // Maximum blocks the projectile can travel
  private float baseMultiplier = 1.0f;

  // Spell school enumeration
  public enum SpellSchool {
    FIRE(0, "fire", COLOR_FIRE),
    WATER(1, "water", COLOR_WATER),
    EARTH(2, "earth", COLOR_EARTH),
    AIR(3, "air", COLOR_AIR),
    THUNDER(4, "thunder", COLOR_THUNDER),
    LIGHT(5, "light", COLOR_LIGHT),
    DARK(6, "dark", COLOR_DARK),
    NATURE(7, "nature", COLOR_NATURE),
    ICE(8, "ice", COLOR_ICE),
    ARCANE(9, "arcane", COLOR_ARCANE),
    BLOOD(10, "blood", COLOR_BLOOD),
    CHAOS(11, "chaos", COLOR_CHAOS);

    public final int id;
    public final String name;
    public final int color;

    SpellSchool(int id, String name, int color) {
      this.id = id;
      this.name = name;
      this.color = color;
    }

    public static SpellSchool fromId(int id) {
      for (SpellSchool school : values()) {
        if (school.id == id)
          return school;
      }
      return FIRE; // Default fallback
    }

    public static SpellSchool fromName(String name) {
      for (SpellSchool school : values()) {
        if (school.name.equals(name))
          return school;
      }
      return FIRE; // Default fallback
    }
  }

  /**
   * Constructor for SpellProjectileEntity.
   * Used for entity spawning from the registry.
   */
  public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, World world) {
    super(entityType, world);
    this.setNoGravity(false);
  }

  /**
   * Constructor for creating spell projectiles from casters.
   *
   * @param world       The world to spawn in
   * @param caster      The entity casting the spell
   * @param spellSchool The school of magic
   * @param spellName   Name of the spell
   * @param damage      Base damage value
   */
  public SpellProjectileEntity(World world, LivingEntity caster, SpellSchool spellSchool,
      String spellName, float damage) {
    super(EntityType.ARROW, world); // Using ARROW as base type (will be replaced with custom type)
    this.setOwner(caster);
    this.setSpellSchool(spellSchool.id);
    this.setSpellName(spellName);
    this.setDamage(damage);
    this.setLifetimeTicks(600); // 30 seconds default

    // Position at caster's eye level
    this.setPosition(caster.getEyePos());
  }

  // ==================== DATA TRACKER ====================

  @Override
  protected void initDataTracker(DataTracker.Builder builder) {
    builder.add(SPELL_SCHOOL, SpellSchool.FIRE.id);
    builder.add(DAMAGE, 10.0f);
    builder.add(LIFETIME_TICKS, 600);
    builder.add(SPELL_NAME, "spell_projectile");
  }

  // ==================== GETTERS & SETTERS ====================

  public void setSpellSchool(int schoolId) {
    this.dataTracker.set(SPELL_SCHOOL, schoolId);
  }

  public SpellSchool getSpellSchool() {
    return SpellSchool.fromId(this.dataTracker.get(SPELL_SCHOOL));
  }

  public void setDamage(float damage) {
    this.dataTracker.set(DAMAGE, Math.max(0.0f, damage));
  }

  public float getDamage() {
    return this.dataTracker.get(DAMAGE);
  }

  public void setLifetimeTicks(int ticks) {
    this.dataTracker.set(LIFETIME_TICKS, ticks);
  }

  public int getLifetimeTicks() {
    return this.dataTracker.get(LIFETIME_TICKS);
  }

  public void setSpellName(String name) {
    this.dataTracker.set(SPELL_NAME, name);
  }

  public String getSpellName() {
    return this.dataTracker.get(SPELL_NAME);
  }

  public void setMaxTravelDistance(int distance) {
    this.maxTravelDistance = distance;
  }

  public int getMaxTravelDistance() {
    return this.maxTravelDistance;
  }

  // ==================== LIFECYCLE ====================

  @Override
  public void tick() {
    super.tick();

    // Increment air ticks
    this.ticksInAir++;

    // Check lifetime
    if (!this.getEntityWorld().isClient()) {
      if (this.ticksInAir > this.getLifetimeTicks()) {
        this.discard();
        return;
      }

      // Check distance traveled
      if (this.ticksInAir * this.getVelocity().length() > this.maxTravelDistance) {
        this.discard();
        return;
      }
    }

    // Update rotation based on velocity
    this.updateRotation();
  }

  // ==================== COLLISION HANDLING ====================

  @Override
  protected void onEntityHit(EntityHitResult entityHitResult) {
    Entity target = entityHitResult.getEntity();

    // Don't hit the caster
    Entity owner = this.getOwner();
    if (target == owner || (owner != null && target.isConnectedThroughVehicle(owner))) {
      return;
    }

    // Only damage living entities
    if (target instanceof LivingEntity livingTarget) {
      // Server-side damage only
      if (!this.getEntityWorld().isClient() && this.getEntityWorld() instanceof ServerWorld serverWorld) {
        // Apply spell damage with ServerWorld parameter (1.21.10 requirement)
        float finalDamage = this.getDamage() * this.baseMultiplier;
        livingTarget.damage(serverWorld, this.getEntityWorld().getDamageSources().generic(), finalDamage);

        // Emit game event
        this.getEntityWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, this.getBlockPos(),
            GameEvent.Emitter.of(this, null));
      }

      // Remove projectile
      this.discard();
    }
  }

  @Override
  protected void onCollision(HitResult hitResult) {
    super.onCollision(hitResult);

    // Emit game event for block hits
    if (hitResult.getType() == HitResult.Type.BLOCK) {
      this.getEntityWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(),
          GameEvent.Emitter.of(this, null));
    }

    // Remove projectile on any collision
    if (!this.getEntityWorld().isClient()) {
      this.discard();
    }
  }

  // ==================== NETWORK SYNCHRONIZATION ====================

  @Override
  public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
    Entity owner = this.getOwner();
    return new EntitySpawnS2CPacket(this, entityTrackerEntry, owner == null ? 0 : owner.getId());
  }

  @Override
  public void onSpawnPacket(EntitySpawnS2CPacket packet) {
    super.onSpawnPacket(packet);
    Entity owner = this.getEntityWorld().getEntityById(packet.getEntityData());
    if (owner != null) {
      this.setOwner(owner);
    }
  }

  // ==================== NBT SERIALIZATION (1.21.10 API) ====================

  @Override
  protected void writeCustomData(WriteView view) {
    view.putInt("SpellSchool", this.getSpellSchool().id);
    view.putFloat("Damage", this.getDamage());
    view.putInt("LifetimeTicks", this.getLifetimeTicks());
    view.putString("SpellName", this.getSpellName());
    view.putInt("TicksInAir", this.ticksInAir);
    view.putInt("MaxTravelDistance", this.maxTravelDistance);
  }

  @Override
  protected void readCustomData(ReadView view) {
    this.setSpellSchool(view.getInt("SpellSchool", SpellSchool.FIRE.id));
    this.setDamage(view.getFloat("Damage", 10.0f));
    this.setLifetimeTicks(view.getInt("LifetimeTicks", 600));
    this.setSpellName(view.getString("SpellName", "spell_projectile"));
    this.ticksInAir = view.getInt("TicksInAir", 0);
    this.maxTravelDistance = view.getInt("MaxTravelDistance", 256);
  }

  // ==================== UTILITY METHODS ====================

  /**
   * Get the color for a spell school as an RGB integer.
   */
  public int getSchoolColor() {
    return this.getSpellSchool().color;
  }

  /**
   * Calculate damage with modifiers.
   *
   * @param targetDistance Distance from caster to target
   * @param spellLevel     The spell's level
   * @return Modified damage value
   */
  public float calculateModifiedDamage(double targetDistance, int spellLevel) {
    // Damage falloff over distance: -1% per block
    double distanceFalloff = Math.max(0.0, 1.0 - (targetDistance / this.maxTravelDistance));

    // Spell level multiplier: +1% per level
    double levelMultiplier = 1.0 + (spellLevel * 0.01);

    return (float) (this.getDamage() * distanceFalloff * levelMultiplier * this.baseMultiplier);
  }

  /**
   * Set the damage multiplier (for buffs/debuffs).
   */
  public void setDamageMultiplier(float multiplier) {
    this.baseMultiplier = Math.max(0.0f, multiplier);
  }

  /**
   * Get the distance traveled by this projectile.
   */
  public double getDistanceTraveled() {
    return this.ticksInAir * this.getVelocity().length();
  }
}

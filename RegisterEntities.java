package dk.mosberg.mam.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import dk.mosberg.mam.ManaAndMagic;
import dk.mosberg.mam.entity.projectile.SpellProjectileEntity;

/**
 * Registry class for custom entities in the Mana and Magic mod.
 *
 * Entities registered:
 * - Spell projectiles (for all spell casting)
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class RegisterEntities {

  /**
   * Spell projectile entity type.
   * Used for all spell casting projectiles in the magic system.
   */
  public static final EntityType<SpellProjectileEntity> SPELL_PROJECTILE = register(
      "spell_projectile",
      EntityType.Builder.create(
          (EntityType<SpellProjectileEntity> type, net.minecraft.world.World world) -> new SpellProjectileEntity(type,
              world),
          SpawnGroup.MISC)
          .dimensions(0.25f, 0.25f) // FIX: Use dimensions(float, float)
          .maxTrackingRange(128)
          .trackingTickInterval(20)
          .build(createRegistryKey("spell_projectile")) // FIX: Add RegistryKey
  );

  /**
   * Create a registry key for an entity type.
   * Required for EntityType.Builder.build() in 1.21.10.
   *
   * @param id Entity identifier
   * @return RegistryKey for the entity type
   */
  private static RegistryKey<EntityType<?>> createRegistryKey(String id) {
    return RegistryKey.of(
        RegistryKeys.ENTITY_TYPE,
        Identifier.of(ManaAndMagic.MOD_ID, id));
  }

  /**
   * Register an entity type to the game registry.
   *
   * @param <T>        Entity type
   * @param name       Registry identifier (without namespace)
   * @param entityType The entity type to register
   * @return The registered entity type
   */
  private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
    return Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(ManaAndMagic.MOD_ID, name),
        entityType);
  }

  /**
   * Initialize entity registrations.
   * Called from ManaAndMagic main class.
   */
  public static void initialize() {
    ManaAndMagic.LOGGER.info("Registered {} entities", 1);
  }
}

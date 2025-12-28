package dk.mosberg.mam.registry;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

/**
 * Registers all custom entities for the Mana and Magic mod.
 * Includes boss entities, guardians, constructs, and spell effects.
 */
public class RegisterEntities {

  // Boss Entities
  public static final EntityType<HostileEntity> ELEMENTAL_TITAN = register(
      "elemental_titan",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.changing(1.5f, 2.5f))
          .build());

  public static final EntityType<HostileEntity> PRIMORDIAL_BEAST = register(
      "primordial_beast",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.changing(2.0f, 3.0f))
          .build());

  // Guardian Entities
  public static final EntityType<HostileEntity> MANA_GUARDIAN = register(
      "mana_guardian",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.0f, 1.8f))
          .build());

  public static final EntityType<HostileEntity> RITUAL_GUARDIAN = register(
      "ritual_guardian",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.2f, 2.0f))
          .build());

  // Magical Constructs
  public static final EntityType<HostileEntity> ARCANE_GOLEM = register(
      "arcane_golem",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.4f, 2.2f))
          .build());

  public static final EntityType<HostileEntity> MANA_CONSTRUCT = register(
      "mana_construct",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.0f, 1.5f))
          .build());

  // Flying Entities
  public static final EntityType<HostileEntity> PHOENIX = register(
      "phoenix",
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.5f, 1.5f))
          .build());

  public static final EntityType<HostileEntity> LIGHTNING_ELEMENTAL = register(
      "lightning_elemental",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.0f, 2.0f))
          .build());

  // Mystical Entities
  public static final EntityType<HostileEntity> CELESTIAL_BEING = register(
      "celestial_being",
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.8f, 1.8f))
          .build());

  public static final EntityType<HostileEntity> BLOOD_WRAITH = register(
      "blood_wraith",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.9f, 1.9f))
          .build());

  public static final EntityType<HostileEntity> SHADOW_CLONE = register(
      "shadow_clone",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
          .build());

  // Chaos Entities
  public static final EntityType<HostileEntity> CHAOS_FRAGMENT = register(
      "chaos_fragment",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.7f, 1.0f))
          .build());

  public static final EntityType<HostileEntity> VOID_ENTITY = register(
      "void_entity",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.1f, 2.1f))
          .build());

  // Projectiles and Effects
  public static final EntityType<ProjectileEntity> SPELL_PROJECTILE = register(
      "spell_projectile",
      FabricEntityTypeBuilder.create(SpawnGroup.MISC, ProjectileEntity::new)
          .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
          .build());

  public static final EntityType<HostileEntity> MANA_ORB = register(
      "mana_orb",
      FabricEntityTypeBuilder.create(SpawnGroup.MISC, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
          .build());

  // Environmental Entities
  public static final EntityType<HostileEntity> STORM_CLOUD = register(
      "storm_cloud",
      FabricEntityTypeBuilder.create(SpawnGroup.MISC, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(2.0f, 1.5f))
          .build());

  public static final EntityType<HostileEntity> NATURE_SPIRIT = register(
      "nature_spirit",
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.0f, 2.0f))
          .build());

  // Portal and Dimension Entities
  public static final EntityType<HostileEntity> PORTAL_ENTITY = register(
      "portal_entity",
      FabricEntityTypeBuilder.create(SpawnGroup.MISC, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(1.5f, 3.0f))
          .build());

  public static final EntityType<HostileEntity> DIMENSION_RIFT = register(
      "dimension_rift",
      FabricEntityTypeBuilder.create(SpawnGroup.MISC, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(2.0f, 2.0f))
          .build());

  public static final EntityType<HostileEntity> VOID_TENTACLE = register(
      "void_tentacle",
      FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileEntity::new)
          .dimensions(EntityDimensions.fixed(0.5f, 3.0f))
          .build());

  /**
   * Registers an entity type.
   */
  private static <T extends net.minecraft.entity.Entity> EntityType<T> register(
      String name, EntityType<T> entityType) {
    return Registry.register(Registries.ENTITY_TYPE,
        new Identifier("mana_and_magic", name), entityType);
  }

  /**
   * Initializes all entity registrations.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    // Entities are registered during static initialization
  }
}

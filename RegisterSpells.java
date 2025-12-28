package dk.mosberg.mam.registry;

import net.minecraft.util.Identifier;

/**
 * Registers all spells for the Mana and Magic mod.
 * Organizes spells by magical school with complete progression.
 */
public class RegisterSpells {

  // Fire School Spells (6 spells)
  public static final String SPARK = registerSpell("spark", "fire", 1, 10, 2.0f);
  public static final String FLAME_BURST = registerSpell("flame_burst", "fire", 5, 30, 5.0f);
  public static final String FIREBALL = registerSpell("fireball", "fire", 10, 50, 10.0f);
  public static final String MAGMA_ERUPTION = registerSpell("magma_eruption", "fire", 20, 80, 15.0f);
  public static final String INFERNAL_BLADES = registerSpell("infernal_blades", "fire", 35, 120, 20.0f);
  public static final String INFERNAL_CONVERGENCE = registerSpell("infernal_convergence", "fire", 51, 200, 48.0f);

  // Water School Spells (4 spells)
  public static final String HEAL = registerSpell("heal", "water", 1, 15, 8.0f);
  public static final String WATER_SHIELD = registerSpell("water_shield", "water", 8, 40, 5.0f);
  public static final String TIDAL_WAVE = registerSpell("tidal_wave", "water", 18, 70, 12.0f);
  public static final String WAVE_CRASH = registerSpell("wave_crash", "water", 45, 150, 35.0f);

  // Earth School Spells (4 spells)
  public static final String STONE_WALL = registerSpell("stone_wall", "earth", 3, 25, 3.0f);
  public static final String STONE_SPIKE = registerSpell("stone_spike", "earth", 12, 45, 8.0f);
  public static final String EARTHQUAKE = registerSpell("earthquake", "earth", 25, 90, 18.0f);
  public static final String EARTH_SPIKES = registerSpell("earth_spikes", "earth", 40, 140, 28.0f);

  // Air School Spells (3 spells)
  public static final String WIND_SLASH = registerSpell("wind_slash", "air", 2, 20, 4.0f);
  public static final String TEMPEST = registerSpell("tempest", "air", 22, 85, 14.0f);
  public static final String WIND_BURST = registerSpell("wind_burst", "air", 38, 130, 24.0f);

  // Thunder School Spells (5 spells)
  public static final String LIGHTNING_BOLT = registerSpell("lightning_bolt", "thunder", 4, 35, 7.0f);
  public static final String CHAIN_LIGHTNING = registerSpell("chain_lightning", "thunder", 15, 60, 11.0f);
  public static final String STORM_CALL = registerSpell("storm_call", "thunder", 30, 100, 22.0f);
  public static final String STORM_CALLER = registerSpell("storm_caller", "thunder", 48, 180, 44.0f);
  public static final String TEMPEST_UNLEASHED = registerSpell("tempest_unleashed", "thunder", 50, 190, 44.0f);

  // Light School Spells (7 spells)
  public static final String CELESTIAL_STRIKE = registerSpell("celestial_strike", "light", 6, 40, 9.0f);
  public static final String RADIANT_BEAM = registerSpell("radiant_beam", "light", 16, 65, 13.0f);
  public static final String HOLY_LIGHT = registerSpell("holy_light", "light", 28, 95, 19.0f);
  public static final String HOLY_BLAST = registerSpell("holy_blast", "light", 42, 145, 32.0f);
  public static final String CHAIN_HEAL = registerSpell("chain_heal", "light", 32, 110, 25.0f);
  public static final String CELESTIAL_BEAM = registerSpell("celestial_beam", "light", 52, 210, 50.0f);
  public static final String RADIANT_JUDGMENT = registerSpell("radiant_judgment", "light", 54, 220, 52.0f);

  // Dark School Spells (5 spells)
  public static final String DARK_CURSE = registerSpell("dark_curse", "dark", 7, 38, 6.0f);
  public static final String SHADOW_STRIKE = registerSpell("shadow_strike", "dark", 14, 55, 10.0f);
  public static final String SHADOW_BOLT = registerSpell("shadow_bolt", "dark", 24, 80, 16.0f);
  public static final String VOID_PIERCE = registerSpell("void_pierce", "dark", 44, 155, 36.0f);
  public static final String VOID_LANCE = registerSpell("void_lance", "dark", 55, 230, 54.0f);

  // Nature School Spells (4 spells)
  public static final String NATURE_BLESSING = registerSpell("nature_blessing", "nature", 5, 30, 7.0f);
  public static final String VINE_GROWTH = registerSpell("vine_growth", "nature", 13, 50, 9.0f);
  public static final String FOREST_CALL = registerSpell("forest_call", "nature", 31, 105, 23.0f);
  public static final String VERDANT_SURGE = registerSpell("verdant_surge", "nature", 46, 165, 40.0f);

  // Ice School Spells (5 spells)
  public static final String ICE_SPIKE = registerSpell("ice_spike", "ice", 2, 18, 3.5f);
  public static final String FROST_BOLT = registerSpell("frost_bolt", "ice", 9, 42, 6.0f);
  public static final String FROST_ARMOR = registerSpell("frost_armor", "ice", 19, 72, 11.0f);
  public static final String GLACIAL_PRISON = registerSpell("glacial_prison", "ice", 34, 125, 26.0f);
  public static final String ICE_COMET = registerSpell("ice_comet", "ice", 49, 185, 46.0f);

  // Arcane School Spells (5 spells)
  public static final String ARCANE_MISSILE = registerSpell("arcane_missile", "arcane", 1, 12, 1.5f);
  public static final String MANA_SHIELD = registerSpell("mana_shield", "arcane", 11, 48, 8.0f);
  public static final String SPELL_WEAVE = registerSpell("spell_weave", "arcane", 36, 135, 27.0f);
  public static final String TEMPORAL_REWIND = registerSpell("temporal_rewind", "arcane", 50, 195, 47.0f);
  public static final String OMEGA_STRIKE = registerSpell("omega_strike", "arcane", 55, 240, 52.0f);

  // Blood School Spells (4 spells)
  public static final String BLOOD_DRAIN = registerSpell("blood_drain", "blood", 8, 45, 12.0f);
  public static final String LIFE_STEAL = registerSpell("life_steal", "blood", 21, 75, 14.0f);
  public static final String BLOOD_SACRIFICE = registerSpell("blood_sacrifice", "blood", 39, 135, 29.0f);
  public static final String BLOODTHIRST = registerSpell("bloodthirst", "blood", 53, 215, 51.0f);

  // Chaos School Spells (3 spells)
  public static final String CHAOS_ORB = registerSpell("chaos_orb", "chaos", 17, 65, 11.0f);
  public static final String CHAOS_STRIKE = registerSpell("chaos_strike", "chaos", 37, 140, 28.0f);
  public static final String CHAOS_RIFT_STRIKE = registerSpell("chaos_rift_strike", "chaos", 43, 150, 34.0f);

  // Multi-School Spells (2 spells)
  public static final String HYBRID_FUSION = registerSpell("hybrid_fusion", "multi_school", 26, 95, 17.0f);
  public static final String SPELLWEAVER_COMBO = registerSpell("spellweaver_combo", "multi_school", 47, 175, 42.0f);

  // Endgame Spells
  public static final String DIMENSIONAL_RIFT = registerSpell("dimensional_rift", "dark", 56, 250, 55.0f);
  public static final String PRIMAL_SURGE = registerSpell("primal_surge", "nature", 57, 255, 56.0f);
  public static final String CHAOS_MAELSTROM = registerSpell("chaos_maelstrom", "chaos", 58, 260, 57.0f);
  public static final String CELESTIAL_NEXUS = registerSpell("celestial_nexus", "light", 59, 265, 58.0f);
  public static final String BLOOD_ECLIPSE = registerSpell("blood_eclipse", "blood", 60, 270, 59.0f);

  /**
   * Helper method to register a spell and return its identifier.
   */
  private static String registerSpell(String name, String school,
      int unlockLevel, int manaCost, float damage) {
    String id = school + "/" + name;
    // Spell registration would normally connect to JSON data
    // This creates the namespace identifier
    return new Identifier("mana_and_magic", id).toString();
  }

  /**
   * Initializes all spell registrations.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    // Spells are registered through JSON data in data/mana_and_magic/spells/
    // This class serves as a reference point for all spell identifiers
  }
}

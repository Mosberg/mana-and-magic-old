package dk.mosberg.mam.registry;

import net.minecraft.util.Identifier;

/**
 * Registers all rituals for the Mana and Magic mod.
 * Organizes rituals by progression level and complexity.
 */
public class RegisterRituals {

  // Early Game Rituals (Levels 1-30)
  public static final String RESURRECTION_CIRCLE = registerRitual("resurrection_circle", 5, 100, 2.0f);
  public static final String SUMMONING_CIRCLE = registerRitual("summoning_circle", 10, 150, 3.0f);
  public static final String TELEPORT_CIRCLE = registerRitual("teleport_circle", 15, 120, 2.5f);
  public static final String BARRIER_WARD = registerRitual("barrier_ward", 12, 140, 2.8f);
  public static final String ELEMENTAL_CONVERGENCE = registerRitual("elemental_convergence", 20, 180, 4.0f);

  // Mid-Tier Rituals (Levels 31-50)
  public static final String SPELL_AMPLIFY = registerRitual("spell_amplify", 25, 200, 5.0f);
  public static final String MANA_WELLSPRING = registerRitual("mana_wellspring", 30, 220, 5.5f);
  public static final String PLANAR_SHIFT = registerRitual("planar_shift", 35, 240, 6.0f);
  public static final String REALITY_ANCHOR = registerRitual("reality_anchor", 40, 260, 6.5f);
  public static final String METAMORPHOSIS_CIRCLE = registerRitual("metamorphosis_circle", 32, 230, 5.8f);
  public static final String NATURE_REBIRTH = registerRitual("nature_rebirth", 38, 250, 6.2f);
  public static final String TIME_FREEZE = registerRitual("time_freeze", 42, 270, 6.8f);

  // Advanced Rituals (Levels 51-75)
  public static final String APOTHEOSIS_RITUAL = registerRitual("apotheosis_ritual", 50, 300, 8.0f);
  public static final String PRIMORDIAL_SUMMONING = registerRitual("primordial_summoning", 55, 320, 8.5f);
  public static final String SPELL_EVOLUTION = registerRitual("spell_evolution", 60, 340, 9.0f);
  public static final String COSMIC_ALIGNMENT = registerRitual("cosmic_alignment", 65, 360, 9.5f);
  public static final String INFINITE_MANA_WELL = registerRitual("infinite_mana_well", 70, 380, 10.0f);
  public static final String ASCENSION_TO_MASTER = registerRitual("ascension_to_master", 75, 400, 10.5f);

  /**
   * Helper method to register a ritual and return its identifier.
   */
  private static String registerRitual(String name, int unlockLevel,
      int manaCost, float power) {
    // Ritual registration connects to JSON data
    // This creates the namespace identifier
    return new Identifier("mana_and_magic", "rituals/" + name).toString();
  }

  /**
   * Initializes all ritual registrations.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    // Rituals are registered through JSON data in data/mana_and_magic/rituals/
    // This class serves as a reference point for all ritual identifiers
  }
}

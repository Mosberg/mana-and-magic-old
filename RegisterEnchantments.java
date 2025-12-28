package dk.mosberg.mam.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import dk.mosberg.mam.ManaAndMagic;

/**
 * Registry for magic-related enchantments
 * Minecraft 1.21.10 - Fabric
 */
public class RegisterEnchantments {

  public static final RegistryEntry<Enchantment> SPELL_EFFICIENCY = register("spell_efficiency",
      new MagicEnchantment(0.5f, 3));

  public static final RegistryEntry<Enchantment> SPELL_POTENCY = register("spell_potency",
      new MagicEnchantment(0.75f, 3));

  public static final RegistryEntry<Enchantment> MANA_CAPACITY = register("mana_capacity",
      new MagicEnchantment(1.0f, 3));

  public static final RegistryEntry<Enchantment> SPELLWEAVER = register("spellweaver",
      new MagicEnchantment(1.5f, 1));

  public static void register() {
    // Enchantments registered automatically
  }

  private static RegistryEntry<Enchantment> register(String name, Enchantment enchantment) {
    return Registry.registerReference(Registries.ENCHANTMENT, ManaAndMagic.id(name), enchantment);
  }

  /**
   * Base enchantment class for magic items
   */
  public static class MagicEnchantment extends Enchantment {
    private final float power;
    private final int maxLevel;

    public MagicEnchantment(float power, int maxLevel) {
      super(null);
      this.power = power;
      this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
      return maxLevel;
    }

    @Override
    public int getMinPower(int level) {
      return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
      return this.getMinPower(level) + 50;
    }
  }
}

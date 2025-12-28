package dk.mosberg.mam.registry;

import java.util.function.Function;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import dk.mosberg.mam.ManaAndMagic;
import dk.mosberg.mam.item.SpellBookItem;
import dk.mosberg.mam.item.SpellBookOpenItem;
import dk.mosberg.mam.item.StaffItem;

/**
 * Registry for all custom items in Mana and Magic.
 *
 * Uses the NEW 1.21.10 registration pattern with RegistryKey and
 * Items.register()
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class RegisterItems {

  // ✅ Items registered with the new 1.21.10 pattern
  public static final Item NOVICE_SPELLBOOK = register(
      "spell_book_novice",
      SpellBookItem::new,
      new Item.Settings().maxCount(1));

  public static final Item NOVICE_SPELLBOOK_OPEN = register(
      "spell_book_novice_open",
      SpellBookOpenItem::new,
      new Item.Settings().maxCount(1));

  public static final Item NOVICE_STAFF = register(
      "staff_novice",
      StaffItem::new,
      new Item.Settings().maxCount(1).maxDamage(100));

  /**
   * Register an item using the 1.21.10+ pattern with RegistryKey.
   * This prevents "Item id not set" errors.
   *
   * @param path     Item path (name)
   * @param factory  Factory function to create the item
   * @param settings Item settings
   * @return The registered item
   */
  public static Item register(
      String path,
      Function<Item.Settings, Item> factory,
      Item.Settings settings) {
    // Create the registry key FIRST
    final RegistryKey<Item> registryKey = RegistryKey.of(
        RegistryKeys.ITEM,
        Identifier.of(ManaAndMagic.MOD_ID, path));

    // Use Items.register() which properly sets the ID BEFORE construction
    return Items.register(registryKey, factory, settings);
  }

  /**
   * Initialize item registrations.
   * This triggers static field initialization.
   */
  public static void initialize() {
    ManaAndMagic.LOGGER.info("✅ Registered {} items", 3);
  }
}

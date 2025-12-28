package dk.mosberg.mam.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import dk.mosberg.mam.ManaAndMagic;

/**
 * Custom creative mode item groups for Mana and Magic.
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class RegisterItemGroups {

  public static ItemGroup MANA_AND_MAGIC;

  /**
   * Initialize item groups.
   * Called from ManaAndMagic main class.
   */
  public static void initialize() {
    MANA_AND_MAGIC = Registry.register(
        Registries.ITEM_GROUP,
        Identifier.of(ManaAndMagic.MOD_ID, "mana_and_magic"),
        FabricItemGroup.builder()
            .icon(() -> new ItemStack(RegisterItems.NOVICE_SPELLBOOK))
            .displayName(Text.translatable("itemGroup.mana_and_magic"))
            .entries((displayContext, entries) -> {
              // Spell Books
              entries.add(RegisterItems.NOVICE_SPELLBOOK);
              entries.add(RegisterItems.NOVICE_SPELLBOOK_OPEN);

              // Catalysts
              entries.add(RegisterItems.NOVICE_STAFF);
            })
            .build());

    ManaAndMagic.LOGGER.info("Registered {} item groups", 1);
  }
}

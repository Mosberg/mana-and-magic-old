package dk.mosberg.mam.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

/**
 * Registers all items for the Mana and Magic mod.
 * Handles mana items, catalysts, spell books, and decorative items.
 */
public class RegisterItems {

  // Mana Items
  public static final Item MANA_CRYSTAL = register("mana_crystal",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item MANA_SHARD = register("mana_shard",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item MANA_BOTTLE = register("mana_bottle",
      new Item(new Item.Settings().maxCount(32)));
  public static final Item MANA_NODE_ITEM = register("mana_node_item",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item ESSENCE_VIAL = register("essence_vial",
      new Item(new Item.Settings().maxCount(16)));

  // Spell Items
  public static final Item SPELL_TOME = register("spell_tome",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_SCROLL = register("spell_scroll",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item SPELL_COMPONENT = register("spell_component",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item GRIMOIRE = register("grimoire",
      new Item(new Item.Settings().maxCount(1)));

  // Catalyst Items
  public static final Item WAND_NOVICE = register("wand_novice",
      new Item(new Item.Settings().maxDamage(256)));
  public static final Item STAFF_NOVICE = register("staff_novice",
      new Item(new Item.Settings().maxDamage(512)));
  public static final Item STAFF_APPRENTICE = register("staff_apprentice",
      new Item(new Item.Settings().maxDamage(768)));
  public static final Item STAFF_ADEPT = register("staff_adept",
      new Item(new Item.Settings().maxDamage(1024)));
  public static final Item STAFF_MASTER = register("staff_master",
      new Item(new Item.Settings().maxDamage(2048)));

  // Orbs and Artifacts
  public static final Item ORB_NOVICE = register("orb_novice",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item CATALYST_ARTIFACT = register("catalyst_artifact",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item ENCHANTED_GEM = register("enchanted_gem",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item RITUAL_FOCUS = register("ritual_focus",
      new Item(new Item.Settings().maxCount(1)));

  // Spell Books
  public static final Item SPELL_BOOK_NOVICE = register("spell_book_novice",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_NOVICE_OPEN = register("spell_book_novice_open",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_APPRENTICE = register("spell_book_apprentice",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_APPRENTICE_OPEN = register("spell_book_apprentice_open",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_ADEPT = register("spell_book_adept",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_ADEPT_OPEN = register("spell_book_adept_open",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_MASTER = register("spell_book_master",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item SPELL_BOOK_MASTER_OPEN = register("spell_book_master_open",
      new Item(new Item.Settings().maxCount(1)));

  // Candy Items
  public static final Item CANDY_CANE_BLUE_WHITE = register("candy_cane_blue_white",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item CANDY_CANE_GREEN_WHITE = register("candy_cane_green_white",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item CANDY_CANE_RED_WHITE = register("candy_cane_red_white",
      new Item(new Item.Settings().maxCount(64)));

  public static final Item WRAPPED_CANDY_BLUE = register("wrapped_candy_blue",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item WRAPPED_CANDY_GREEN = register("wrapped_candy_green",
      new Item(new Item.Settings().maxCount(64)));
  public static final Item WRAPPED_CANDY_RED = register("wrapped_candy_red",
      new Item(new Item.Settings().maxCount(64)));

  // Gift Box Items
  public static final Item GIFT_BOX_CLOSED = register("gift_box_closed",
      new Item(new Item.Settings().maxCount(1)));
  public static final Item GIFT_BOX_OPEN = register("gift_box_open",
      new Item(new Item.Settings().maxCount(1)));

  // Item Group
  public static final ItemGroup MANA_AND_MAGIC_GROUP = FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.mana_and_magic"))
      .icon(() -> new ItemStack(MANA_CRYSTAL))
      .build();

  /**
   * Registers a single item and adds it to the item registry.
   */
  private static Item register(String name, Item item) {
    return Registry.register(Registries.ITEM,
        new Identifier("mana_and_magic", name), item);
  }

  /**
   * Initializes all item registrations.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    Registry.register(Registries.ITEM_GROUP,
        new Identifier("mana_and_magic", "mana_and_magic_group"),
        MANA_AND_MAGIC_GROUP);

    ItemGroupEvents.modifyContents(MANA_AND_MAGIC_GROUP, itemGroupContent -> {
      itemGroupContent.add(MANA_CRYSTAL);
      itemGroupContent.add(MANA_SHARD);
      itemGroupContent.add(MANA_BOTTLE);
      itemGroupContent.add(ESSENCE_VIAL);
      itemGroupContent.add(SPELL_TOME);
      itemGroupContent.add(SPELL_SCROLL);
      itemGroupContent.add(WAND_NOVICE);
      itemGroupContent.add(STAFF_NOVICE);
      itemGroupContent.add(STAFF_APPRENTICE);
      itemGroupContent.add(STAFF_ADEPT);
      itemGroupContent.add(STAFF_MASTER);
      itemGroupContent.add(SPELL_BOOK_NOVICE);
      itemGroupContent.add(SPELL_BOOK_APPRENTICE);
      itemGroupContent.add(SPELL_BOOK_ADEPT);
      itemGroupContent.add(SPELL_BOOK_MASTER);
    });
  }
}

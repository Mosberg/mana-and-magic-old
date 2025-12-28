package dk.mosberg.mam.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.block.v1.FabricBlock;

/**
 * Registers all blocks for the Mana and Magic mod.
 * Handles mana infrastructure, crafting blocks, and decorative blocks.
 */
public class RegisterBlocks {

  // Mana Infrastructure Blocks
  public static final Block MANA_NODE_BLOCK = register("mana_node_block",
      new Block(FabricBlock.copyOf(Blocks.CRYING_OBSIDIAN)
          .luminance(state -> 8)
          .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

  public static final Block MANA_CRYSTAL_BLOCK = register("mana_crystal_block",
      new Block(FabricBlock.copyOf(Blocks.AMETHYST_BLOCK)
          .luminance(state -> 12)
          .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

  public static final Block MANA_LEY_LINE = register("mana_ley_line",
      new Block(FabricBlock.copyOf(Blocks.PURPUR_BLOCK)
          .luminance(state -> 6)
          .sounds(BlockSoundGroup.DEEPSLATE)));

  public static final Block MANA_LAMP = register("mana_lamp",
      new Block(FabricBlock.copyOf(Blocks.GLOWSTONE)
          .luminance(state -> 15)
          .sounds(BlockSoundGroup.GLASS)));

  public static final Block MANA_INFUSER = register("mana_infuser",
      new Block(FabricBlock.copyOf(Blocks.CRAFTING_TABLE)
          .luminance(state -> 10)
          .sounds(BlockSoundGroup.WOOD)));

  // Crafting Blocks
  public static final Block SPELL_ALTAR = register("spell_altar",
      new Block(FabricBlock.copyOf(Blocks.DARK_OAK_LOG)
          .luminance(state -> 8)
          .sounds(BlockSoundGroup.STONE)));

  public static final Block SPELL_AMPLIFIER = register("spell_amplifier",
      new Block(FabricBlock.copyOf(Blocks.OBSIDIAN)
          .luminance(state -> 7)
          .sounds(BlockSoundGroup.STONE)));

  public static final Block ARCANE_WORKBENCH = register("arcane_workbench",
      new Block(FabricBlock.copyOf(Blocks.CRAFTING_TABLE)
          .luminance(state -> 9)
          .sounds(BlockSoundGroup.WOOD)));

  public static final Block RITUAL_CIRCLE_BLOCK = register("ritual_circle_block",
      new Block(FabricBlock.copyOf(Blocks.DARK_PRISMARINE)
          .luminance(state -> 11)
          .sounds(BlockSoundGroup.DEEPSLATE)));

  // Special Blocks
  public static final Block VOID_ESSENCE_BLOCK = register("void_essence_block",
      new Block(FabricBlock.copyOf(Blocks.END_STONE)
          .luminance(state -> 5)
          .sounds(BlockSoundGroup.STONE)));

  public static final Block ARCANE_TREE_LOG = register("arcane_tree_log",
      new Block(FabricBlock.copyOf(Blocks.DARK_OAK_LOG)
          .sounds(BlockSoundGroup.WOOD)));

  public static final Block ARCANE_TREE_LEAVES = register("arcane_tree_leaves",
      new Block(FabricBlock.copyOf(Blocks.DARK_OAK_LEAVES)
          .sounds(BlockSoundGroup.GRASS)));

  // Decorative Blocks
  public static final Block GIFT_BOX_CLOSED = register("gift_box_closed",
      new Block(FabricBlock.copyOf(Blocks.OAK_WOOD)
          .sounds(BlockSoundGroup.WOOD)));

  public static final Block GIFT_BOX_OPEN = register("gift_box_open",
      new Block(FabricBlock.copyOf(Blocks.OAK_WOOD)
          .sounds(BlockSoundGroup.WOOD)));

  /**
   * Registers a block and creates a corresponding block item.
   */
  private static Block register(String name, Block block) {
    Block registeredBlock = Registry.register(Registries.BLOCK,
        new Identifier("mana_and_magic", name), block);

    Registry.register(Registries.ITEM,
        new Identifier("mana_and_magic", name),
        new BlockItem(registeredBlock, new Item.Settings()));

    return registeredBlock;
  }

  /**
   * Initializes all block registrations.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    // Blocks are registered during static initialization
  }
}

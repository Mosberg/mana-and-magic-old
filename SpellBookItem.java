package dk.mosberg.mam.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * Closed spell book item.
 * Right-click to open and access spells.
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class SpellBookItem extends Item {

  public SpellBookItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity player, Hand hand) {
    if (!world.isClient()) {
      player.sendMessage(
          Text.literal("Opening spell book..."),
          true);
    }

    return ActionResult.SUCCESS;
  }
}

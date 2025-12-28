package dk.mosberg.mam.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import dk.mosberg.mam.entity.projectile.SpellProjectileEntity;
import dk.mosberg.mam.registry.RegisterEntities;

/**
 * Catalyst item for casting spells.
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class StaffItem extends Item {

  public StaffItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);

    if (!world.isClient() && world instanceof ServerWorld serverWorld
        && player instanceof ServerPlayerEntity serverPlayer) {
      SpellProjectileEntity projectile = new SpellProjectileEntity(
          RegisterEntities.SPELL_PROJECTILE,
          world);

      projectile.setOwner(player);
      projectile.setPosition(player.getEyePos());
      projectile.setVelocity(
          player,
          player.getPitch(),
          player.getYaw(),
          0.0f,
          2.0f,
          1.0f);

      projectile.setSpellSchool(SpellProjectileEntity.SpellSchool.FIRE.id);
      projectile.setDamage(10.0f);
      projectile.setSpellName("fire_strike");

      world.spawnEntity(projectile);

      itemStack.damage(1, serverWorld, serverPlayer,
          (item) -> serverPlayer.sendEquipmentBreakStatus(this, EquipmentSlot.MAINHAND));

      player.getItemCooldownManager().set(itemStack, 20);
    }

    return ActionResult.SUCCESS;
  }
}

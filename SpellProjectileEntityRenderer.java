package dk.mosberg.mam.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import dk.mosberg.mam.entity.projectile.SpellProjectileEntity;

// Custom renderer class (advanced)

public class SpellProjectileEntityRenderer extends FlyingItemEntityRenderer<SpellProjectileEntity> {
  public SpellProjectileEntityRenderer(EntityRendererFactory.Context context) {
    super(context);
  }
  // Optionally override render() for custom effects
}

package dk.mosberg.mam.client.registry;

import net.minecraft.client.render.entity.EntityRendererFactories;

import dk.mosberg.mam.ManaAndMagic;
import dk.mosberg.mam.registry.RegisterEntities;

/**
 * Registers entity renderers on the client side.
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class RegisterEntityRenderers {

  /**
   * Register all entity renderers.
   * Called from ManaAndMagicClient.
   */
  public static void initialize() {
    // Register spell projectile renderer

    EntityRendererFactories.register(
        RegisterEntities.SPELL_PROJECTILE,
        dk.mosberg.mam.client.render.SpellProjectileEntityRenderer::new);

    ManaAndMagic.LOGGER.info("✅ Registered entity renderers");
  }
}

package dk.mosberg.mam.client;

import net.fabricmc.api.ClientModInitializer;

import dk.mosberg.mam.ManaAndMagic;
import dk.mosberg.mam.client.registry.RegisterClientEvents;
import dk.mosberg.mam.client.registry.RegisterEntityRenderers;

/**
 * Client-side initialization for Mana and Magic.
 *
 * @author Mosberg
 * @version 1.0.0
 */
public class ManaAndMagicClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ManaAndMagic.LOGGER.info("✅ Initializing Mana and Magic Client");

    // Register entity renderers
    RegisterEntityRenderers.initialize();

    // Register client events (keybindings, etc.)
    RegisterClientEvents.register();

    ManaAndMagic.LOGGER.info("✅ Mana and Magic Client initialized");
  }
}

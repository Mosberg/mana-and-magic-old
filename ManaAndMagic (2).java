package dk.mosberg.mam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;

import dk.mosberg.mam.core.ManaManager;
import dk.mosberg.mam.core.RitualManager;
import dk.mosberg.mam.core.SpellEngine;
import dk.mosberg.mam.network.ManaPacketHandler;
import dk.mosberg.mam.registry.*;

/**
 * Main mod initializer - runs on BOTH client and server.
 * Minecraft 1.21.10 - Fabric Split Sources
 * Use ManaAndMagicClient for client-only initialization.
 */
public class ManaAndMagic implements ModInitializer {
  public static final String MOD_ID = "mana_and_magic";

  public static final Logger LOGGER = LoggerFactory.getLogger("Mana & Magic");

  @Override
  public void onInitialize() {
    LOGGER.info("⚡ Initializing Mana & Magic for Minecraft 1.21.10");

    // Register all core systems (server-safe)
    registerItems();
    registerBlocks();
    registerEntities();
    registerEnchantments();
    registerAttributes();
    registerSpells();
    registerRituals();
    registerNetworking();

    // Initialize managers (no client code!)
    ManaManager.register();
    SpellEngine.initialize();
    RitualManager.initialize();

    // Register network payloads
    ManaPacketHandler.registerCommonPayloads();

    // Register event handlers (server-side)
    registerServerEvents();

    LOGGER.info("✨ Mana & Magic initialized successfully!");
  }

  private void registerItems() {
    RegisterItems.register();
    LOGGER.debug("Registered items");
  }

  private void registerBlocks() {
    RegisterBlocks.register();
    LOGGER.debug("Registered blocks");
  }

  private void registerEntities() {
    RegisterEntities.register();
    LOGGER.debug("Registered entities");
  }

  private void registerEnchantments() {
    RegisterEnchantments.register();
    LOGGER.debug("Registered enchantments");
  }

  private void registerAttributes() {
    RegisterAttributes.register();
    LOGGER.debug("Registered attributes");
  }

  private void registerSpells() {
    RegisterSpells.register();
    LOGGER.debug("Registered 200+ spells");
  }

  private void registerRituals() {
    RegisterRituals.register();
    LOGGER.debug("Registered rituals");
  }

  private void registerNetworking() {
    RegisterNetworking.register();
    LOGGER.debug("Registered networking");
  }

  private void registerServerEvents() {
    // Tick event - safe for server
    ServerTickEvents.END_SERVER_TICK.register(server -> {
      server.getPlayerManager().getPlayerList().forEach(player -> {
        ManaManager.tick(player);
        SpellEngine.tick(player);
        RitualManager.tick(player);
      });
    });

    LOGGER.debug("Server events registered");
  }

  /**
   * Create a mod-namespaced Identifier (1.21.10 compatible)
   */
  public static Identifier id(String path) {
    return Identifier.of(MOD_ID, path);
  }

  /**
   * Check if running on client
   */
  public static boolean isClient() {
    return FabricLoader.getInstance().getEnvironmentType().isClient();
  }

  /**
   * Check if running on server/dedicated server
   */
  public static boolean isServer() {
    return !isClient();
  }
}

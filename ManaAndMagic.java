package dk.mosberg.mam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

import dk.mosberg.mam.registry.RegisterEntities;
import dk.mosberg.mam.registry.RegisterItemGroups;
import dk.mosberg.mam.registry.RegisterItems;

/**
 * Main mod initializer for Mana and Magic.
 * Handles registration of all mod content.
 *
 * @author Mosberg
 * @version 0.0.1
 */
public class ManaAndMagic implements ModInitializer {

  public static final String MOD_ID = "mana_and_magic";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    LOGGER.info("✅ Initializing Mana and Magic v0.0.1");

    // Register items
    RegisterItems.initialize();

    // Register entities
    RegisterEntities.initialize();

    // Register item groups
    RegisterItemGroups.initialize();

    LOGGER.info("✅ Mana and Magic initialized successfully!");
  }
}

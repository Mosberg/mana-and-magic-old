package dk.mosberg.mam.registry;

import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * Registers all network packets for the Mana and Magic mod.
 * Handles client-server communication for spells, rituals, and mana
 * synchronization.
 */
public class RegisterNetworking {

  // Packet Identifiers
  public static final Identifier CAST_SPELL_PACKET = new Identifier("mana_and_magic", "cast_spell");

  public static final Identifier SYNC_MANA_POOLS_PACKET = new Identifier("mana_and_magic", "sync_mana_pools");

  public static final Identifier RITUAL_COMPLETE_PACKET = new Identifier("mana_and_magic", "ritual_complete");

  public static final Identifier LEARN_SPELL_PACKET = new Identifier("mana_and_magic", "learn_spell");

  public static final Identifier MANA_BURN_PACKET = new Identifier("mana_and_magic", "mana_burn");

  public static final Identifier SPELL_COOLDOWN_PACKET = new Identifier("mana_and_magic", "spell_cooldown");

  public static final Identifier DUAL_CAST_PACKET = new Identifier("mana_and_magic", "dual_cast");

  public static final Identifier SYNERGY_TRIGGER_PACKET = new Identifier("mana_and_magic", "synergy_trigger");

  /**
   * Registers cast spell packet handler.
   */
  private static void registerCastSpellPacket() {
    ServerPlayNetworking.registerGlobalReceiver(CAST_SPELL_PACKET,
        (server, player, handler, buf, responseSender) -> {
          // Read packet data
          String spellName = buf.readString();
          float manaUsed = buf.readFloat();

          // Handle spell casting on server
          server.execute(() -> {
            // Cast spell logic here
          });
        });
  }

  /**
   * Registers mana synchronization packet handler.
   */
  private static void registerSyncManaPools() {
    ServerPlayNetworking.registerGlobalReceiver(SYNC_MANA_POOLS_PACKET,
        (server, player, handler, buf, responseSender) -> {
          // Read mana pool data
          int personalMana = buf.readInt();
          int auraMana = buf.readInt();
          int reserveMana = buf.readInt();

          // Synchronize mana pools
          server.execute(() -> {
            // Sync logic here
          });
        });
  }

  /**
   * Registers ritual complete packet handler.
   */
  private static void registerRitualComplete() {
    ServerPlayNetworking.registerGlobalReceiver(RITUAL_COMPLETE_PACKET,
        (server, player, handler, buf, responseSender) -> {
          // Read ritual data
          String ritualName = buf.readString();

          // Handle ritual completion
          server.execute(() -> {
            // Ritual completion logic here
          });
        });
  }

  /**
   * Registers learn spell packet handler.
   */
  private static void registerLearnSpell() {
    ServerPlayNetworking.registerGlobalReceiver(LEARN_SPELL_PACKET,
        (server, player, handler, buf, responseSender) -> {
          // Read spell data
          String spellId = buf.readString();
          int level = buf.readInt();

          // Learn spell on server
          server.execute(() -> {
            // Learn spell logic here
          });
        });
  }

  /**
   * Initializes all network packet handlers.
   * Should be called from the main mod initializer.
   */
  public static void register() {
    registerCastSpellPacket();
    registerSyncManaPools();
    registerRitualComplete();
    registerLearnSpell();
  }
}

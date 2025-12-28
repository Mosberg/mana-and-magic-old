package dk.mosberg.mam.core;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import dk.mosberg.mam.core.data.PlayerManaComponent;

/**
 * Manages player mana pools and regeneration
 * Minecraft 1.21.10 - Fabric
 */
public class ManaManager {

    private static final String MANA_DATA_KEY = "mana_data";

    public static void register() {
        // Register tick events and data handlers
    }

    /**
     * Get or create player mana data
     */
    public static PlayerManaComponent getManaData(ServerPlayerEntity player) {
        NbtCompound nbt = player.getAttachedModData();

        if (!nbt.contains(MANA_DATA_KEY)) {
            nbt.put(MANA_DATA_KEY, new NbtCompound());
        }

        return new PlayerManaComponent(nbt.getCompound(MANA_DATA_KEY));
    }

    /**
     * Drain mana from player
     */
    public static boolean drainMana(ServerPlayerEntity player, float amount, ManaPool pool) {
        PlayerManaComponent data = getManaData(player);

        if (data.getMana(pool) >= amount) {
            data.setMana(pool, data.getMana(pool) - amount);
            syncManaToClient(player, data);
            return true;
        }

        return false;
    }

    /**
     * Restore mana to player
     */
    public static void restoreMana(ServerPlayerEntity player, float amount, ManaPool pool) {
        PlayerManaComponent data = getManaData(player);
        float maxMana = data.getMaxMana(pool);
        float currentMana = data.getMana(pool);

        data.setMana(pool, Math.min(currentMana + amount, maxMana));
        syncManaToClient(player, data);
    }

    /**
     * Tick mana regeneration
     */
    public static void tick(ServerPlayerEntity player) {
        PlayerManaComponent data = getManaData(player);

        // Regenerate mana based on config
        float regenRate = 0.5f; // Config value
        data.regenMana(regenRate);

        syncManaToClient(player, data);
    }

    /**
     * Sync mana data to client
     */
    private static void syncManaToClient(ServerPlayerEntity player, PlayerManaComponent data) {
        // Send sync payload to client
        // Implementation depends on network setup
    }

    public enum ManaPool {
        PERSONAL(500),
        AURA(200),
        RESERVE(1000);

        private final float maxMana;

        ManaPool(float maxMana) {
            this.maxMana = maxMana;
        }

        public float getMaxMana() {
            return maxMana;
        }
    }
}

package dk.mosberg.mam.core.data;

import net.minecraft.nbt.NbtCompound;
import dk.mosberg.mam.core.ManaManager;

/**
 * Stores player mana data
 * Minecraft 1.21.10 - Fabric
 */
public class PlayerManaComponent {

    private final NbtCompound nbt;

    public PlayerManaComponent(NbtCompound nbt) {
        this.nbt = nbt;

        // Initialize defaults if needed
        if (!nbt.contains("personal")) nbt.putFloat("personal", 500f);
        if (!nbt.contains("aura")) nbt.putFloat("aura", 200f);
        if (!nbt.contains("reserve")) nbt.putFloat("reserve", 1000f);
    }

    public float getMana(ManaManager.ManaPool pool) {
        return nbt.getFloat(pool.name().toLowerCase());
    }

    public void setMana(ManaManager.ManaPool pool, float amount) {
        nbt.putFloat(pool.name().toLowerCase(), Math.max(0, amount));
    }

    public float getMaxMana(ManaManager.ManaPool pool) {
        return pool.getMaxMana();
    }

    public void regenMana(float rate) {
        for (ManaManager.ManaPool pool : ManaManager.ManaPool.values()) {
            float current = getMana(pool);
            float max = getMaxMana(pool);
            if (current < max) {
                setMana(pool, Math.min(current + rate, max));
            }
        }
    }

    public NbtCompound getNbt() {
        return nbt;
    }
}

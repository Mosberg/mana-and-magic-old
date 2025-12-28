package dk.mosberg.mam.magic.spell;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import dk.mosberg.mam.core.ManaManager;

/**
 * Abstract base class for all spells
 * Minecraft 1.21.10 - Fabric
 */
public abstract class Spell {

    protected final String id;
    protected final String school;
    protected final int tier;
    protected final float manaCost;
    protected final float castTime;
    protected final float cooldown;

    public Spell(String id, String school, int tier, float manaCost, float castTime, float cooldown) {
        this.id = id;
        this.school = school;
        this.tier = tier;
        this.manaCost = manaCost;
        this.castTime = castTime;
        this.cooldown = cooldown;
    }

    public abstract void cast(ServerPlayerEntity player, SpellContext context);

    public boolean canCast(ServerPlayerEntity player, SpellContext context) {
        // Check mana
        PlayerManaComponent data = ManaManager.getManaData(player);

        if (data.getMana(ManaManager.ManaPool.PERSONAL) < manaCost) {
            return false;
        }

        // Check level requirements
        // Check other requirements

        return true;
    }

    public String getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public int getTier() {
        return tier;
    }

    public float getManaCost() {
        return manaCost;
    }

    public float getCastTime() {
        return castTime;
    }

    public float getCooldown() {
        return cooldown;
    }
}

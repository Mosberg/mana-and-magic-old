package dk.mosberg.mam.core;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import dk.mosberg.mam.magic.spell.Spell;
import dk.mosberg.mam.magic.spell.SpellContext;
import dk.mosberg.mam.magic.spell.SpellRegistry;

/**
 * Manages spell casting and execution
 * Minecraft 1.21.10 - Fabric
 */
public class SpellEngine {

    public static void initialize() {
        // Initialize spell systems
    }

    /**
     * Cast a spell
     */
    public static void castSpell(ServerPlayerEntity player, String spellId, Vec3d targetPos) {
        Spell spell = SpellRegistry.getSpell(spellId);

        if (spell == null) {
            return;
        }

        // Create spell context
        SpellContext context = new SpellContext(player, spell, targetPos);

        // Check requirements
        if (!spell.canCast(player, context)) {
            return;
        }

        // Cast spell
        spell.cast(player, context);
    }

    /**
     * Tick active spells
     */
    public static void tick(ServerPlayerEntity player) {
        // Update cooldowns and channel spells
    }
}

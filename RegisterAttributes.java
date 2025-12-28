package dk.mosberg.mam.registry;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import dk.mosberg.mam.ManaAndMagic;

/**
 * Registry for custom entity attributes (Mana-related)
 * Minecraft 1.21.10 - Fabric
 */
public class RegisterAttributes {

    public static final EntityAttribute MANA_REGEN = register("mana_regen",
        new EntityAttribute("attribute.mana_magic.mana_regen", 1.0).setTracked(true)
    );

    public static final EntityAttribute SPELL_POWER = register("spell_power",
        new EntityAttribute("attribute.mana_magic.spell_power", 1.0).setTracked(true)
    );

    public static final EntityAttribute MANA_EFFICIENCY = register("mana_efficiency",
        new EntityAttribute("attribute.mana_magic.mana_efficiency", 1.0).setTracked(true)
    );

    public static void register() {
        // Attributes registered automatically
    }

    private static EntityAttribute register(String name, EntityAttribute attribute) {
        return Registry.register(Registries.ATTRIBUTE, ManaAndMagic.id(name), attribute);
    }
}

# Mana and Magic (Old) - Comprehensive README

## 📖 Overview

**Mana and Magic (Arcane Magic System)** is a sophisticated Minecraft Fabric mod for **version 1.21.10** that introduces an immersive magical system with **13 distinct schools of magic**, **13 ritual categories**, and **15 gemstone variants**. This repository (`mana-and-magic-old`) represents the original development foundation of the Arcane Magic System mod by Mosberg, built with **Java 21** and the **Fabric modding framework**.

The mod transforms Minecraft into a full-fledged magical RPG experience, allowing players to harness arcane forces through spell-casting, ritual performance, and gemstone enchantment. Every aspect is data-driven through JSON configurations, minimizing hardcoded values and maximizing flexibility for modpack developers and server administrators.

---

## 🎯 Project Structure

### Directory Organization

```
mana-and-magic-old/
├── src/main/
│   ├── java/
│   │   └── dk/mosberg/mam/          # Main mod package namespace
│   │       ├── core/                 # Core initialization & registration
│   │       ├── spell/                # Spell system implementation
│   │       ├── ritual/               # Ritual mechanics
│   │       ├── gemstone/             # Gemstone system
│   │       ├── mana/                 # Mana pool & regeneration
│   │       ├── network/              # Client-server synchronization
│   │       └── util/                 # Utility classes & helpers
│   └── resources/
│       ├── assets/mam/               # Client-side assets
│       │   ├── textures/
│       │   ├── models/
│       │   ├── particles/
│       │   └── lang/
│       ├── data/mam/
│       │   ├── spells/               # Spell JSON definitions
│       │   ├── rituals/              # Ritual JSON definitions
│       │   ├── gemstones/            # Gemstone configurations
│       │   └── worldgen/
│       └── fabric.mod.json           # Fabric loader manifest
├── src/client/
│   ├── java/
│   │   └── dk/mosberg/mam/client/   # Client-only code
│   │       ├── screen/               # GUI screens & widgets
│   │       ├── rendering/            # Particle & entity rendering
│   │       ├── keybind/              # Input handling
│   │       └── hud/                  # HUD overlays
│   └── resources/
└── build.gradle                      # Gradle build configuration
```

---

## 🔮 Core Magic System

### **13 Schools of Magic**

The mod implements a comprehensive school-based magic system, each with distinct mechanics and visual effects:

| School | Symbol | Type | Primary Function |
|--------|--------|------|------------------|
| **Air** 🌀 | Wind Spiral | Utility | Movement, protection, wind effects |
| **Arcane** 🔮 | Crystal Orb | Support | Knowledge, sensing, enchantment |
| **Blood** 🩸 | Dripping Drop | Life | Healing, damage, sacrifice |
| **Chaos** 🌪️ | Turbulent Vortex | Destructive | Unpredictability, disruption |
| **Dark** 🌑 | Shadow | Corrupt | Stealth, curses, shadow magic |
| **Earth** 🌍 | Stone Block | Defense | Mining, protection, structure |
| **Fire** 🔥 | Flame | Offensive | Damage, destruction, warmth |
| **Ice** ❄️ | Snowflake | Control | Freezing, slowing, manipulation |
| **Light** ✨ | Star | Healing | Restoration, revelation, blessing |
| **Nature** 🌿 | Leaf | Growth | Summoning, growth, life |
| **Thunder** ⚡ | Lightning Bolt | Force | Damage, speed, electricity |
| **Void** 🕳️ | Black Hole | Extreme | Absorption, teleportation, nullification |
| **Water** 💧 | Wave | Adaptive | Freezing, flowing, adaptation |

Each school integrates into the spell framework through JSON definitions and is tied to specific gemstone affinities.

### **13 Ritual Categories**

Rituals represent large-scale magical workings requiring preparation, resources, and geometric patterns:

1. **Ascension** - Elevation & progression magic
2. **Circle** - Containment & protection rituals
3. **Cosmic** - Celestial alignment & astronomy
4. **Elemental** - Harnessing raw elements
5. **Fountain** - Mana & energy flow manipulation
6. **Planar** - Dimensional traversal
7. **Reality** - World manipulation & alteration
8. **Resurrection** - Life restoration & revival
9. **Sacrifice** - Requiring player resources/mana
10. **Summoning** - Entity & construct creation
11. **Temporal** - Time-based effects
12. **Transformation** - Form & property alteration
13. **Vortex** - Energy concentration & release

### **15 Gemstone Variants**

A full progression system of gemstones, each with unique properties and rarity tiers:

**Common Tier:**
- **Hematite** - Iron-based, foundational magic
- **Carnelian** - Fire association, energy focus

**Uncommon Tier:**
- **Citrine** - Solar properties, clarity
- **Jade** - Earth harmony, stability
- **Peridot** - Light reflection, growth

**Rare Tier:**
- **Sodalite** - Communication, truth
- **Apatite** - Healing, motivation
- **Aquamarine** - Water harmony, calm

**Epic Tier:**
- **Moonstone** - Lunar cycles, intuition
- **Rhodonite** - Emotional balance, compassion
- **Topaz** - Strength, protection

**Legendary Tier:**
- **Tourmaline** - Multi-colored, versatility
- **Ruby** - Fire mastery, passion
- **Sapphire** - Wisdom, tranquility
- **Tanzanite** - Rarest, exceptional power

---

## 📁 Key Java Files & Responsibilities

### Core Initialization Files

#### `ManaAndMagic.java` (Main Initializer)
```java
public class ManaAndMagic implements ModInitializer {
    public static final String MOD_ID = "mam";
    public static final Logger LOGGER = LoggerFactory.getLogger("Mana and Magic");
    
    @Override
    public void onInitialize() {
        // Block registration
        // Item registration
        // Spell system initialization
        // Ritual system initialization
        // Gemstone registration
        // Mana system setup
        // Event listener registration
    }
}
```
**Responsibilities:**
- Serves as the mod's primary entry point
- Coordinates initialization of all major systems
- Registers blocks, items, entities, and particle types
- Sets up networking for client-server synchronization

#### `ManaAndMagicClient.java` (Client Initializer)
```java
public class ManaAndMagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Screen/GUI registration
        // Particle effect registration
        // Entity renderer registration
        // Keybinding setup
        // Client event listeners
        // HUD overlay registration
    }
}
```
**Responsibilities:**
- Client-side initialization (render thread)
- Registers all visual components
- Handles input via keybindings
- Sets up HUD elements for mana display

#### `ManaAndMagicConfig.java`
**Responsibilities:**
- Manages mod configuration (server-side rules)
- Spell balance parameters (mana costs, cooldowns)
- Ritual requirements (ingredients, timing)
- World generation settings (ore density, feature frequency)
- Difficulty scaling options

#### `ManaAndMagicClientModMenu.java`
**Responsibilities:**
- Integration with Mod Menu mod for settings UI
- Provides user-friendly configuration interface
- Handles custom widgets for magic system options
- Client-side preference management

### System-Specific Files

#### `ManaAndMagicDataGenerator.java`
**Responsibilities:**
- Automatic JSON asset generation
- Block model & texture data generation
- Language file (i18n) generation for all spell/ritual/gemstone names
- Loot table generation for magical items
- Recipe generation for crafting

---

## 🛠️ Technical Architecture

### Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Modern language features (records, pattern matching, text blocks) |
| **Minecraft** | 1.21.10 | Game version |
| **Fabric Loader** | 0.18.3 | Mod loading & versioning |
| **Fabric API** | 0.138.4 | Core modding hooks & utilities |
| **Yarn Mappings** | 1.21.10+build.3 | Human-readable class/method names |
| **Gradle** | 9.2.1 | Build automation |
| **SLF4J** | 2.0.17 | Logging framework |
| **Gson** | 2.13.2 | JSON parsing |

### Design Patterns

#### **Data-Driven Architecture**
All spell, ritual, and gemstone definitions are stored as JSON files in `data/mam/`, enabling:
- Easy balance adjustments without recompilation
- Modpack customization via datapack override
- Community content creation

Example Spell JSON:
```json
{
  "id": "mam:fireball",
  "school": "fire",
  "tier": 2,
  "manaCost": 15.0,
  "cooldown": 2.5,
  "gemstones": ["ruby", "carnelian"],
  "components": {
    "projectile": "fireball",
    "damage": 8.0,
    "radius": 3.5
  }
}
```

#### **Registry Pattern**
Uses Fabric's registry system for type-safe, namespaced registration:
```java
Registry.register(BuiltInRegistries.BLOCK, 
    Identifier.of("mam", "mana_pool"), 
    new ManaPoolBlock());
```

#### **Component-Based Spell System**
Spells are composed of reusable components:
- **Projectile Component** - Creates flying spell objects
- **Damage Component** - Applies harm with school affinity
- **Status Component** - Applies potion effects
- **Teleport Component** - Warps player/entities
- **Animation Component** - Visual/particle effects

#### **Networking for Multiplayer**
Client-server packets for:
- Spell casting (confirmed on server)
- Mana pool synchronization
- Ritual state updates
- Gemstone equipment changes

---

## 🎮 Gameplay Systems

### Mana Pool System

**Storage & Regeneration:**
- Each player has a persistent mana pool (default: 1000 points)
- Natural regeneration: ~1 point per 2 ticks (10 per second)
- Affected by armor enchantments, rituals, and environmental factors
- Synced to client for HUD display

**Spell Casting Flow:**
```
1. Player presses keybind → cast spell
2. Server validates player has sufficient mana
3. Mana consumed from pool
4. Spell effect spawned (particles, damage, etc.)
5. Cooldown timer activated
6. Mana regeneration begins (if depleted)
```

### Spell-Casting Mechanics

**Spell Attributes:**
- **Tier**: 1-5, determines unlock progression and power
- **Mana Cost**: Base consumption (modified by gear/buffs)
- **Cooldown**: Seconds before recast possible
- **Gemstone Requirements**: Which gems enhance this spell
- **School Affinity**: Primary magic school

**Casting Process:**
1. Check player has gems for the school
2. Verify sufficient mana & cooldown expired
3. Calculate final mana cost (enchantments apply reductions)
4. Execute spell effect logic
5. Display particle effects to all watching players
6. Apply cooldown locally & server-side

### Ritual System

**Ritual Execution:**
1. **Preparation Phase**: Place ritual blocks in geometric pattern
2. **Ingredient Phase**: Insert required items/gemstones
3. **Activation**: Right-click ritual center with staff
4. **Channeling**: Perform input gestures (wand drawing patterns)
5. **Completion**: Effect triggers, ingredients consumed

**Example - Ritual of Ascension:**
- Pattern: 5x5 circle on ground
- Ingredients: 4 Amethyst, 2 Gold Ingots, 1 Enchanted Book
- Gemstone: Tanzanite (legendary)
- Effect: Levitate player for 60 seconds + Speed II

### Gemstone Mechanics

**Gemstone Slots:**
- Helmet: 2 gem slots
- Chestplate: 3 gem slots  
- Leggings: 2 gem slots
- Boots: 2 gem slots
- **Total: 9 slots per player**

**Gem Benefits:**
- School Power Boost: +15% spell damage per matching school gem
- Mana Capacity: +100 per Epic/Legendary gem equipped
- Cooldown Reduction: -5% per gem (stacks to -40% cap)
- Special Perks: Rare/Legendary gems grant unique abilities

**Installation:**
- Craft Gemstone Socket (Crafting Table)
- Place socket on gear via anvil
- Insert gem into socket to activate bonus

---

## 📝 JSON Configuration System

### Spell Definition Schema

```json
{
  "id": "namespace:spell_name",
  "school": "fire|air|water|earth|etc",
  "tier": 1,
  "manaCost": 10.5,
  "cooldown": 1.5,
  "castTime": 0.5,
  "range": 16.0,
  "gemstones": ["ruby", "carnelian"],
  "components": {
    "projectile": {
      "type": "fireball",
      "speed": 1.2,
      "lifetime": 5.0
    },
    "damage": {
      "baseAmount": 6.0,
      "school": "fire",
      "canCritical": true
    },
    "area": {
      "radius": 3.5,
      "affectsPlayer": false
    }
  },
  "requirements": {
    "minLevel": 1,
    "minGemstones": 1,
    "environment": "any"
  }
}
```

### Ritual Definition Schema

```json
{
  "id": "namespace:ritual_name",
  "category": "summoning|transformation|etc",
  "tier": 2,
  "ingredients": [
    { "item": "minecraft:diamond", "count": 2 },
    { "item": "mam:essence_of_fire", "count": 4 }
  ],
  "gemstones": ["ruby", "sapphire"],
  "pattern": {
    "type": "circle",
    "radius": 4,
    "blocks": "ritual_block"
  },
  "duration": 20,
  "effect": {
    "type": "summon",
    "entity": "mam:fire_elemental",
    "count": 1,
    "lifetime": 300
  }
}
```

### Gemstone Configuration

```json
{
  "id": "namespace:ruby",
  "rarity": "legendary",
  "school": "fire",
  "properties": {
    "manaCostReduction": 0.05,
    "schoolPowerBoost": 0.15,
    "maxManaBonus": 200
  },
  "texture": "assets/mam/textures/gemstone/ruby.png",
  "color": "FF0000"
}
```

---

## 🔧 Core File Responsibilities Summary

| File | Primary Duty | Dependencies |
|------|--------------|--------------|
| `ManaAndMagic.java` | System initialization | All subsystems |
| `ManaAndMagicClient.java` | Client rendering setup | Rendering, input |
| `ManaAndMagicConfig.java` | Configuration management | JSON loader |
| `ManaAndMagicClientModMenu.java` | Settings UI | Mod Menu, Config |
| `ManaAndMagicDataGenerator.java` | Asset generation | Gradle, DataGen API |

---

## 🎯 Mod Features

### For Players

✅ **Learn & Progress**: 5-tier spell progression system  
✅ **Customize Gameplay**: Equip gemstones to personalize magic  
✅ **Master Rituals**: Complex multi-step magical workings  
✅ **Visual Spectacle**: Particle effects, animations, immersive sounds  
✅ **Exploration**: Find magical structures & rare gemstones  
✅ **PvP Magic**: Cast spells on other players (server-configurable)  

### For Developers

✅ **JSON-Based**: Modify spells without touching Java  
✅ **Open Architecture**: Easy to add new schools/rituals  
✅ **Datapack Compatible**: Override files via resource packs  
✅ **Comprehensive Logging**: SLF4J integration for debugging  
✅ **Type-Safe Registries**: Fabric's registry system  
✅ **Documented Code**: Javadoc on all public APIs  

---

## 🚀 Building & Installation

### Prerequisites

- **Java 21** (or later)
- **Gradle 9.2.1+**
- **Fabric Loader 0.18.3+**
- **Minecraft 1.21.10 (Java Edition)**

### Build Instructions

```bash
# Clone repository
git clone https://github.com/Mosberg/mana-and-magic-old.git
cd mana-and-magic-old

# Build mod JAR
./gradlew build

# JAR located at: build/libs/mana-and-magic-[version].jar
```

### Installation

1. Download the compiled `.jar` from releases
2. Place in `%APPDATA%\.minecraft\mods` (Windows) or `~/.minecraft/mods` (Linux/Mac)
3. Ensure **Fabric Loader** and **Fabric API** are installed
4. Launch Minecraft with Fabric profile

---

## 📊 Version Information

| Component | Version |
|-----------|---------|
| Minecraft | 1.21.10 |
| Fabric Loader | 0.18.3 |
| Fabric API | 0.138.4+1.21.10 |
| Yarn Mappings | 1.21.10+build.3 |
| Java | 21 |
| Gradle | 9.2.1 |
| Gson | 2.13.2 |
| SLF4J | 2.0.17 |

---

## 🎓 Development Guidelines

### Code Style

- **Naming**: PascalCase for classes, camelCase for methods/fields
- **Constants**: UPPER_SNAKE_CASE
- **Line Length**: 120 characters maximum
- **Indentation**: 4 spaces
- **Javadoc**: Required on all public methods/classes

### Required Documentation

```java
/**
 * Manages player mana pools and regeneration mechanics.
 * 
 * This class is responsible for storing, regenerating, and consuming
 * player mana during spell-casting events.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaPoolManager {
    /**
     * Consumes mana from the player's pool.
     *
     * @param player The target player entity
     * @param amount The mana amount to consume
     * @return {@code true} if successfully consumed, {@code false} if insufficient mana
     * @throws InsufficientManaException if player lacks required mana
     */
    public boolean consumeMana(@NotNull PlayerEntity player, float amount) 
            throws InsufficientManaException {
        // Implementation
    }
}
```

### Logging Best Practices

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpellCaster {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellCaster.class);
    
    public void castSpell(PlayerEntity player, Spell spell) {
        try {
            LOGGER.info("Player {} casting spell: {}", player.getName(), spell.getId());
            // Execution
        } catch (Exception e) {
            LOGGER.error("Spell casting failed for player {}", player.getName(), e);
        }
    }
}
```

---

## 🔗 Important References

- **Fabric Documentation**: https://docs.fabricmc.net
- **Yarn Mappings**: https://mappings.dev (1.21.10)
- **Fabric API Javadocs**: https://maven.fabricmc.net/docs/
- **Gradle Guide**: https://docs.gradle.org/9.2.1/userguide/

---

## 📜 License & Attribution

**Repository**: Mana and Magic (Old) - Mosberg  
**License**: Check LICENSE file in repository  
**Framework**: Fabric Loader & Fabric API  
**Minecraft**: Developed by Mojang Studios  

---

## 🤝 Contributing

When contributing to this project:

1. Follow the **Code Style** guidelines above
2. Add comprehensive **Javadoc** comments
3. Test changes in-game on Minecraft 1.21.10
4. Use **SLF4J** for logging (not `System.out.println`)
5. Keep **JSON configs** aligned with provided schemas
6. Submit pull requests with clear descriptions

---

## 📞 Support & Community

For issues, feature requests, or questions:
- Check existing GitHub issues
- Review Fabric documentation
- Consult Minecraft modding communities (Discord, Reddit, Forums)

---

**Last Updated**: December 28, 2025  
**Maintained By**: Mosberg  
**Framework**: Fabric 0.18.3 | Minecraft 1.21.10 | Java 21
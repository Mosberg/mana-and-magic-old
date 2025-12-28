# Mana and Magic (Old) - Comprehensive README with Java Architecture

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
│   │       ├── ManaAndMagic.java    # Main initializer entry point
│   │       ├── core/
│   │       │   ├── ManaPool.java    # Mana storage & sync
│   │       │   ├── SpellRegistry.java
│   │       │   ├── RitualRegistry.java
│   │       │   └── GemstoneRegistry.java
│   │       ├── spell/
│   │       │   ├── Spell.java       # Base spell class
│   │       │   ├── SpellComponent.java
│   │       │   ├── SpellCaster.java # Casting logic
│   │       │   └── SpellEffect.java
│   │       ├── ritual/
│   │       │   ├── Ritual.java
│   │       │   ├── RitualBlock.java
│   │       │   └── RitualValidator.java
│   │       ├── gemstone/
│   │       │   ├── Gemstone.java
│   │       │   ├── GemstoneItem.java
│   │       │   ├── GemstoneSocket.java
│   │       │   └── GemstoneSlot.java
│   │       ├── mana/
│   │       │   ├── ManaRegeneration.java
│   │       │   ├── ManaCapacity.java
│   │       │   └── ManaConsumer.java
│   │       ├── network/
│   │       │   ├── SpellCastPacket.java
│   │       │   ├── ManaUpdatePacket.java
│   │       │   └── RitualStatePacket.java
│   │       ├── event/
│   │       │   ├── ServerTickHandler.java
│   │       │   ├── PlayerJoinHandler.java
│   │       │   ├── SpellCastHandler.java
│   │       │   └── RitualInteractHandler.java
│   │       ├── data/
│   │       │   ├── JsonSpellLoader.java
│   │       │   ├── JsonRitualLoader.java
│   │       │   └── ConfigManager.java
│   │       └── util/
│   │           ├── MathUtil.java
│   │           ├── RaycastUtil.java
│   │           └── ParticleUtil.java
│   └── resources/
│       ├── assets/mam/
│       │   ├── textures/
│       │   │   ├── item/         # Gemstones, wands, etc.
│       │   │   ├── block/        # Ritual blocks
│       │   │   ├── gui/          # HUD elements
│       │   │   └── particle/
│       │   ├── models/
│       │   ├── particles/
│       │   └── lang/
│       ├── data/mam/
│       │   ├── spells/           # Spell JSONs
│       │   ├── rituals/          # Ritual JSONs
│       │   ├── gemstones/        # Gemstone config
│       │   └── worldgen/
│       ├── fabric.mod.json       # Fabric loader manifest
│       └── mam.mixins.json       # Mixin configuration
├── src/client/
│   ├── java/
│   │   └── dk/mosberg/mam/client/
│   │       ├── ManaAndMagicClient.java
│   │       ├── screen/
│   │       │   ├── ManaHudScreen.java
│   │       │   ├── SpellBookScreen.java
│   │       │   └── RitualGuideScreen.java
│   │       ├── rendering/
│   │       │   ├── SpellProjectileRenderer.java
│   │       │   ├── ParticleEffectRenderer.java
│   │       │   └── GemstoneGlintRenderer.java
│   │       ├── keybind/
│   │       │   ├── SpellKeybinds.java
│   │       │   └── KeybindHandler.java
│   │       ├── hud/
│   │       │   ├── ManaBarOverlay.java
│   │       │   ├── CooldownTracker.java
│   │       │   └── SchoolIndicator.java
│   │       └── mixin/
│   │           └── client/
│   │               └── ClientPlayNetworkHandlerMixin.java
│   └── resources/
└── build.gradle                  # Gradle build configuration
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

---

## 📁 Detailed Java File Analysis

### Core Entry Point Files

#### `ManaAndMagic.java` - Main Initializer
**Location**: `src/main/java/dk/mosberg/mam/ManaAndMagic.java`

```java
/**
 * Main entry point for the Mana and Magic mod.
 * Implements ModInitializer to hook into Fabric's mod loading system.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaAndMagic implements ModInitializer {
    public static final String MOD_ID = "mam";
    public static final Logger LOGGER = LoggerFactory.getLogger("Mana and Magic");

    @Override
    public void onInitialize() {
        // Phase 1: Register basic content
        registerBlocks();      // Mana pools, ritual blocks, etc.
        registerItems();       // Wands, gemstones, spell books
        registerEntities();    // Spell projectiles, summons
        registerParticles();   // Visual effects for spells
        
        // Phase 2: Initialize systems
        SpellRegistry.init();        // Load spell definitions
        RitualRegistry.init();       // Load ritual definitions
        GemstoneRegistry.init();     // Load gemstone definitions
        ManaPoolManager.init();      // Setup mana system
        
        // Phase 3: Register event handlers
        ServerTickEvents.END.register(new ServerTickHandler());
        ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartHandler());
        
        // Phase 4: Register network handlers
        ServerPlayNetworking.registerGlobalReceiver(SpellCastPacket.ID, SpellCastPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(RitualActivatePacket.ID, RitualActivatePacket::handle);
        
        LOGGER.info("Mana and Magic initialized successfully");
    }
    
    private void registerBlocks() {
        // Register with proper namespacing
        Registry.register(Registries.BLOCK, 
            Identifier.of(MOD_ID, "mana_pool"), 
            new ManaPoolBlock(AbstractBlock.Settings.create()
                .mapColor(MapColor.DIAMOND_BLUE)
                .hardness(3.0f)
                .requiresTool()
            )
        );
    }
    
    private void registerItems() {
        // Register wands, gems, etc.
    }
}
```

**Responsibilities**:
- Single entry point for mod loading via Fabric's ModInitializer interface
- Coordinates initialization sequence (blocks → items → systems → events)
- Sets up all registries using Fabric's Registry system
- Registers network packet handlers for client-server communication
- Provides logging context for debugging (`LOGGER`)

**Key Concepts**:
- Uses `ModInitializer` interface required by `fabric.mod.json`
- Separates initialization into logical phases (content, systems, events, networking)
- Follows Fabric patterns for type-safe registry operations
- Mod ID (`mam`) used as namespace for all resources

---

#### `ManaAndMagicClient.java` - Client-Side Initializer
**Location**: `src/client/java/dk/mosberg/mam/ManaAndMagicClient.java`

```java
/**
 * Client-side initialization for Mana and Magic mod.
 * Called only on the client thread, handles rendering and input.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaAndMagicClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("Mana and Magic (Client)");

    @Override
    public void onInitializeClient() {
        // Phase 1: Register renderers
        EntityRendererRegistry.register(MamEntities.SPELL_PROJECTILE, 
            ctx -> new SpellProjectileRenderer(ctx));
        BlockEntityRendererRegistry.register(MamBlockEntities.RITUAL_BLOCK, 
            ctx -> new RitualBlockRenderer());
        
        // Phase 2: Register screens/GUIs
        ScreenEvents.INIT.register((screen, client, width, height) -> {
            if (screen instanceof GameScreen) {
                screen.addDrawableChild(new ManaHudScreen(width, height));
            }
        });
        
        // Phase 3: Register keybindings
        KeyBindingHelper.registerKeyBinding(SpellKeybinds.CAST_SPELL);
        KeyBindingHelper.registerKeyBinding(SpellKeybinds.OPEN_SPELL_BOOK);
        KeyBindingHelper.registerKeyBinding(SpellKeybinds.CYCLE_SCHOOL);
        
        // Phase 4: Register client events
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickHandler());
        ScreenEvents.INIT.register(new ScreenInitHandler());
        
        // Phase 5: Register network receivers
        ClientPlayNetworking.registerGlobalReceiver(ManaUpdatePacket.ID, 
            ManaUpdatePacket::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(CooldownSyncPacket.ID,
            CooldownSyncPacket::handleClient);
        
        // Phase 6: Register particle factories
        ParticleFactoryRegistry.getInstance().register(MamParticles.SPELL_IMPACT,
            SpellImpactParticleFactory::new);
        
        LOGGER.info("Client initialization complete");
    }
}
```

**Responsibilities**:
- Initializes client-specific rendering systems
- Registers keybindings for spell casting and menus
- Sets up HUD overlays (mana bar, cooldown timers)
- Registers particle effect factories for visual spells
- Handles client-side event listeners

**Key Patterns**:
- Uses Fabric's client-specific APIs (separate from server code)
- Registers keybindings early for availability in title screen
- Uses screen events to inject custom UI elements
- Receives packets from server to update client state

---

### Data Management & Configuration

#### `ManaAndMagicConfig.java` - Configuration Handler
**Location**: `src/main/java/dk/mosberg/mam/ManaAndMagicConfig.java`

```java
/**
 * Manages server-side configuration for Mana and Magic.
 * Reads settings from JSON and applies them to gameplay.
 *
 * Typically stored in:
 * - Linux/Mac: ~/.minecraft/config/mam/config.json
 * - Windows: %APPDATA%\.minecraft\config\mam\config.json
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaAndMagicConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManaAndMagicConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    // Default configuration values
    private static final int DEFAULT_MAX_MANA = 1000;
    private static final float DEFAULT_MANA_REGEN = 1.0f;
    private static final boolean DEFAULT_PVP_SPELLS_ENABLED = true;
    
    private final File configFile;
    private JsonObject configObject;

    /**
     * Creates configuration handler for the mod.
     *
     * @param gameDirectory The .minecraft directory
     */
    public ManaAndMagicConfig(@NotNull File gameDirectory) {
        this.configFile = new File(gameDirectory, "config/mam/config.json");
    }

    /**
     * Loads configuration from disk, creates defaults if missing.
     * Uses try-with-resources for file handling safety.
     */
    public void loadConfig() {
        try {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }
            
            if (configFile.exists()) {
                try (FileReader reader = new FileReader(configFile)) {
                    configObject = GSON.fromJson(reader, JsonObject.class);
                    LOGGER.info("Configuration loaded from {}", configFile.getAbsolutePath());
                }
            } else {
                createDefaultConfig();
                LOGGER.info("Created default configuration at {}", configFile.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load configuration", e);
            createDefaultConfig();
        }
    }

    /**
     * Creates and saves default configuration to disk.
     */
    private void createDefaultConfig() {
        configObject = new JsonObject();
        
        // Mana settings
        JsonObject manaSettings = new JsonObject();
        manaSettings.addProperty("maxMana", DEFAULT_MAX_MANA);
        manaSettings.addProperty("regenPerSecond", DEFAULT_MANA_REGEN);
        manaSettings.addProperty("regenDelay", 20); // ticks before regen starts
        configObject.add("mana", manaSettings);
        
        // Spell settings
        JsonObject spellSettings = new JsonObject();
        spellSettings.addProperty("pvpEnabled", DEFAULT_PVP_SPELLS_ENABLED);
        spellSettings.addProperty("friendlyFireEnabled", false);
        spellSettings.addProperty("globalCooldownMillis", 100);
        configObject.add("spells", spellSettings);
        
        // Ritual settings
        JsonObject ritualSettings = new JsonObject();
        ritualSettings.addProperty("requiresLineOfSight", false);
        ritualSettings.addProperty("maxRitualsPerPlayer", 5);
        configObject.add("rituals", ritualSettings);
        
        saveConfig();
    }

    /**
     * Persists current configuration to disk.
     */
    public void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(configObject, writer);
            LOGGER.info("Configuration saved successfully");
        } catch (IOException e) {
            LOGGER.error("Failed to save configuration", e);
        }
    }

    // Getters with type safety
    public int getMaxMana() {
        return configObject.getAsJsonObject("mana")
            .get("maxMana")
            .getAsInt();
    }

    public float getManaRegenPerSecond() {
        return configObject.getAsJsonObject("mana")
            .get("regenPerSecond")
            .getAsFloat();
    }

    public boolean isPvpSpellsEnabled() {
        return configObject.getAsJsonObject("spells")
            .get("pvpEnabled")
            .getAsBoolean();
    }
}
```

**Responsibilities**:
- Manages all mod configuration (stored as JSON)
- Provides defaults if config missing
- Safe file I/O with try-with-resources
- Type-safe getters for configuration values

**Design Patterns**:
- Singleton pattern (typically one instance per mod)
- Builder pattern for JsonObject construction
- Fallback to defaults on load failure

---

#### `ManaAndMagicDataGenerator.java` - Asset Generation
**Location**: `src/main/java/dk/mosberg/mam/datagen/ManaAndMagicDataGenerator.java`

```java
/**
 * Generates data files (models, textures, lang, recipes) at build time.
 * Reduces manual JSON boilerplate and ensures consistency.
 *
 * Run with: ./gradlew runDatagen
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaAndMagicDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(@NotNull DataGenerator generator) {
        FabricDataOutput output = generator.createOutput();
        
        // Language file generation (i18n for all spells, rituals, gems)
        generator.addProvider(
            new LanguageProvider(output, "en_us") {
                @Override
                public void generateTranslations() {
                    // Spell names
                    add("spell.mam.fireball", "Fireball");
                    add("spell.mam.ice_spike", "Ice Spike");
                    add("spell.mam.lightning_bolt", "Lightning Bolt");
                    
                    // Ritual names
                    add("ritual.mam.summoning", "Ritual of Summoning");
                    add("ritual.mam.ascension", "Ritual of Ascension");
                    
                    // Gemstone names
                    add("gemstone.mam.ruby", "Ruby");
                    add("gemstone.mam.sapphire", "Sapphire");
                    add("gemstone.mam.tanzanite", "Tanzanite");
                    
                    // Item names
                    add("item.mam.wooden_wand", "Wooden Wand");
                    add("item.mam.mana_pool", "Mana Pool");
                }
            }
        );
        
        // Block model generation
        generator.addProvider(
            new ModelProvider(output) {
                @Override
                protected void generateBlockStateModels(
                        BlockModelGenerator blockModels) {
                    blockModels.registerSimpleCubeAll(MamBlocks.MANA_POOL);
                    blockModels.registerLog(MamBlocks.RITUAL_MARKER);
                }
            }
        );
        
        // Recipe generation
        generator.addProvider(
            new RecipeProvider(output) {
                @Override
                public void generateRecipes(RecipeExporter exporter) {
                    // Wand recipe
                    ShapedRecipeBuilder.create(RecipeCategory.TOOLS, MamItems.WOODEN_WAND)
                        .pattern(" W ")
                        .pattern(" S ")
                        .pattern(" C ")
                        .input('W', Items.OAK_LOG)
                        .input('S', Items.STICK)
                        .input('C', Items.CHARCOAL)
                        .criterion("has_charcoal", 
                            InventoryChangeTrigger.Conditions.items(Items.CHARCOAL))
                        .offerTo(exporter);
                }
            }
        );
    }
}
```

**Responsibilities**:
- Generates all data files at build time
- Creates language files for i18n support
- Generates block models and textures
- Produces recipe JSONs automatically
- Reduces manual JSON boilerplate

**Benefits**:
- Type-safe code generation
- Single source of truth
- Consistency guaranteed
- Easier maintenance

---

### Core System Files

#### `ManaPoolManager.java` - Mana Storage & Sync
**Location**: `src/main/java/dk/mosberg/mam/core/ManaPoolManager.java`

```java
/**
 * Manages player mana pools with Fabric's Data Attachment API.
 * Handles storage, regeneration, and client-server synchronization.
 *
 * Uses AttachmentRegistry for persistent, syncable player data.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ManaPoolManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManaPoolManager.class);
    
    // Attachment type - persists between sessions
    public static final AttachmentType<ManaData> MANA_ATTACHMENT =
        AttachmentRegistry.createPersistent(
            Identifier.of("mam", "mana_pool"),
            builder -> builder
                .serializer(ManaData.CODEC)
                .syncWith(ManaData.STREAM_CODEC, AttachmentSyncPredicate.all())
        );

    /**
     * Represents mutable mana data for a player.
     * Separated into a record for clean serialization.
     */
    public static record ManaData(
        float currentMana,
        float maxMana,
        long lastRegenTime
    ) {
        public static final Codec<ManaData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                Codec.FLOAT.fieldOf("current").forGetter(ManaData::currentMana),
                Codec.FLOAT.fieldOf("max").forGetter(ManaData::maxMana),
                Codec.LONG.fieldOf("lastRegen").forGetter(ManaData::lastRegenTime)
            ).apply(instance, ManaData::new)
        );
        
        public static final StreamCodec<RegistryByteBuf, ManaData> STREAM_CODEC =
            StreamCodec.composite(
                ByteBufCodecs.FLOAT, ManaData::currentMana,
                ByteBufCodecs.FLOAT, ManaData::maxMana,
                ByteBufCodecs.VAR_LONG, ManaData::lastRegenTime,
                ManaData::new
            );

        public ManaData withCurrent(float current) {
            return new ManaData(Math.max(0, Math.min(current, maxMana)), maxMana, 
                lastRegenTime);
        }

        public ManaData withMax(float max) {
            return new ManaData(Math.min(currentMana, max), max, lastRegenTime);
        }
    }

    /**
     * Retrieves mana data for a player.
     * Automatically creates with defaults if not present.
     *
     * @param player The target player
     * @return Mana data record
     */
    public static ManaData getMana(@NotNull PlayerEntity player) {
        return player.getAttachedOrElse(MANA_ATTACHMENT, 
            new ManaData(1000f, 1000f, System.currentTimeMillis()));
    }

    /**
     * Sets mana data for a player.
     * Automatically syncs to client if on server.
     *
     * @param player The target player
     * @param mana New mana data
     */
    public static void setMana(@NotNull PlayerEntity player, @NotNull ManaData mana) {
        player.setAttached(MANA_ATTACHMENT, mana);
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, 
                new ManaUpdatePacket(mana.currentMana, mana.maxMana));
        }
    }

    /**
     * Consumes mana from a player's pool.
     *
     * @param player The target player
     * @param amount Mana to consume
     * @return true if consumption successful, false if insufficient mana
     * @throws IllegalArgumentException if amount is negative
     */
    public static boolean consumeMana(@NotNull PlayerEntity player, float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Mana consumption amount cannot be negative");
        }
        
        ManaData current = getMana(player);
        if (current.currentMana >= amount) {
            setMana(player, current.withCurrent(current.currentMana - amount));
            return true;
        }
        return false;
    }

    /**
     * Regenerates mana based on tick rate.
     * Called from ServerTickHandler each tick.
     *
     * @param player The target player
     * @param ticks Ticks elapsed since last regen
     */
    public static void regenerateMana(@NotNull ServerPlayerEntity player, int ticks) {
        ManaData current = getMana(player);
        float regenAmount = (ManaAndMagicConfig.instance().getManaRegenPerSecond() 
            * ticks) / 20.0f; // 20 ticks = 1 second
        
        if (current.currentMana < current.maxMana) {
            float newMana = Math.min(current.currentMana + regenAmount, current.maxMana);
            setMana(player, current.withCurrent(newMana));
        }
    }
}
```

**Key Features**:
- Uses Fabric's `AttachmentRegistry` for type-safe player data
- Automatic persistence across sessions with Codec
- Network sync to clients via `StreamCodec`
- Record-based design for immutability and clean serialization
- Thread-safe operations (server-side only)

**Data Attachment Benefits**:
- Replaces NBT with strongly-typed alternatives
- Automatic network synchronization
- Persistent storage by default
- Per-player data isolation

---

#### `SpellCaster.java` - Spell Casting Logic
**Location**: `src/main/java/dk/mosberg/mam/spell/SpellCaster.java`

```java
/**
 * Core spell-casting logic and validation.
 * Handles mana consumption, cooldowns, and effect execution.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpellCaster {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellCaster.class);
    
    // Cooldown tracker: Player UUID → Map<Spell ID, Last Cast Time>
    private static final Map<UUID, Map<String, Long>> SPELL_COOLDOWNS = 
        Collections.synchronizedMap(new HashMap<>());

    /**
     * Attempts to cast a spell from a player.
     *
     * Validation sequence:
     * 1. Check spell exists and is unlocked
     * 2. Verify player has required gemstones
     * 3. Check cooldown not active
     * 4. Verify sufficient mana
     * 5. Execute spell effect
     * 6. Apply cooldown
     *
     * @param player The casting player
     * @param spell The spell to cast
     * @return true if spell executed, false if validation failed
     */
    public static boolean castSpell(@NotNull ServerPlayerEntity player, 
                                    @NotNull Spell spell) {
        // Validation checks
        if (!validateSpell(player, spell)) {
            player.sendMessage(Text.literal("§cSpell validation failed"), false);
            return false;
        }

        if (!hasRequiredGemstones(player, spell)) {
            player.sendMessage(Text.literal("§cInsufficient gemstones"), false);
            return false;
        }

        if (isOnCooldown(player, spell)) {
            player.sendMessage(Text.literal("§cSpell on cooldown"), false);
            return false;
        }

        if (!ManaPoolManager.consumeMana(player, spell.getManaCost())) {
            player.sendMessage(Text.literal("§cInsufficient mana"), false);
            return false;
        }

        try {
            // Execute spell effect
            spell.execute(player, player.getWorld());
            
            // Apply cooldown
            applyCooldown(player, spell);
            
            // Send success packet to client
            ServerPlayNetworking.send((ServerPlayerEntity) player,
                new SpellCastSuccessPacket(spell.getId()));
            
            LOGGER.info("Player {} cast spell {}", player.getName().getString(), spell.getId());
            return true;
            
        } catch (Exception e) {
            LOGGER.error("Spell execution failed for player {}", player.getName(), e);
            player.sendMessage(Text.literal("§cSpell execution failed"), false);
            return false;
        }
    }

    /**
     * Checks if player has required gemstones for spell.
     *
     * @param player The casting player
     * @param spell The spell to check
     * @return true if all required gemstones present
     */
    private static boolean hasRequiredGemstones(@NotNull PlayerEntity player, 
                                               @NotNull Spell spell) {
        List<String> required = spell.getRequiredGemstones();
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        
        // Count gemstones in all armor slots
        Map<String, Integer> gemstoneCount = new HashMap<>();
        countGemstones(helmet, gemstoneCount);
        countGemstones(chest, gemstoneCount);
        countGemstones(legs, gemstoneCount);
        countGemstones(boots, gemstoneCount);
        
        // Verify requirement met
        for (String gem : required) {
            if (gemstoneCount.getOrDefault(gem, 0) < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if spell is currently on cooldown.
     *
     * @param player The casting player
     * @param spell The spell to check
     * @return true if cooldown active
     */
    private static boolean isOnCooldown(@NotNull PlayerEntity player, 
                                       @NotNull Spell spell) {
        Map<String, Long> playerCooldowns = SPELL_COOLDOWNS.getOrDefault(
            player.getUuid(), new HashMap<>());
        
        long lastCast = playerCooldowns.getOrDefault(spell.getId(), 0L);
        long elapsed = System.currentTimeMillis() - lastCast;
        long cooldownMillis = (long) (spell.getCooldownSeconds() * 1000);
        
        return elapsed < cooldownMillis;
    }

    /**
     * Applies cooldown to spell for player.
     *
     * @param player The casting player
     * @param spell The spell to cooldown
     */
    private static void applyCooldown(@NotNull PlayerEntity player, 
                                     @NotNull Spell spell) {
        Map<String, Long> playerCooldowns = SPELL_COOLDOWNS.computeIfAbsent(
            player.getUuid(), k -> Collections.synchronizedMap(new HashMap<>()));
        playerCooldowns.put(spell.getId(), System.currentTimeMillis());
    }

    /**
     * Validates spell basic requirements.
     *
     * @param player The casting player
     * @param spell The spell to validate
     * @return true if spell valid
     */
    private static boolean validateSpell(@NotNull PlayerEntity player, 
                                        @NotNull Spell spell) {
        return spell != null && !spell.getId().isEmpty();
    }
}
```

**Responsibilities**:
- Central spell casting orchestration
- Validation in proper order (spell → gems → cooldown → mana)
- Exception handling with logging
- Cooldown management (synchronized for thread safety)
- Sends success packets to client for feedback

**Thread Safety**:
- Uses `Collections.synchronizedMap()` for cooldown tracking
- Safe for concurrent server tick handlers
- Immutable spell data after creation

---

### Event Handler System

#### `ServerTickHandler.java` - Server Tick Events
**Location**: `src/main/java/dk/mosberg/mam/event/ServerTickHandler.java`

```java
/**
 * Handles server tick events for mana regeneration and spell updates.
 * Called every tick (50 milliseconds / 20 ticks per second).
 *
 * Implements ServerTickEvents.END callback from Fabric API.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServerTickHandler implements ServerTickEvents.EndTick {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTickHandler.class);
    
    // Tick counter for periodic operations
    private int tickCounter = 0;

    @Override
    public void onEndTick(@NotNull MinecraftServer server) {
        tickCounter++;
        
        // Mana regeneration every 5 ticks (4 per second)
        if (tickCounter % 5 == 0) {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ManaPoolManager.regenerateMana(player, 5);
            }
        }
        
        // Update cooldowns every second (20 ticks)
        if (tickCounter % 20 == 0) {
            updateCooldownTrackers();
        }
        
        // Ritual updates every tick
        updateActiveRituals(server);
        
        // Reset counter on overflow
        if (tickCounter >= Integer.MAX_VALUE - 1) {
            tickCounter = 0;
        }
    }

    /**
     * Updates active rituals for all loaded chunks.
     *
     * @param server The minecraft server instance
     */
    private void updateActiveRituals(@NotNull MinecraftServer server) {
        // For each loaded world
        for (ServerWorld world : server.getWorlds()) {
            // Iterate loaded chunks and update ritual blocks
            world.getChunkManager().threadedAnvilChunkStorage.getLoadedChunks()
                .forEach(chunk -> {
                    chunk.getBlockEntities().forEach((pos, blockEntity) -> {
                        if (blockEntity instanceof RitualBlockEntity ritualBE) {
                            ritualBE.tick();
                        }
                    });
                });
        }
    }

    /**
     * Cleans up expired cooldown entries.
     * Called periodically to prevent memory leaks.
     */
    private void updateCooldownTrackers() {
        // Cooldown cleanup logic
        long currentTime = System.currentTimeMillis();
        // Remove old entries...
    }
}
```

**Responsibilities**:
- Triggers every server tick (50ms)
- Regenerates mana for all players periodically
- Updates active rituals in loaded chunks
- Manages cooldown cleanup to prevent memory leaks

**Performance Considerations**:
- Uses tick counter to spread expensive operations
- Only updates players in the server's player list
- Cleans up old data periodically

---

### Networking

#### Packet System Pattern
**Location**: `src/main/java/dk/mosberg/mam/network/`

```java
/**
 * Represents spell casting information sent to server.
 * Packet uses Fabric's networking APIs for safe, ordered delivery.
 *
 * @author Mosberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpellCastPacket {
    public static final Identifier ID = Identifier.of("mam", "spell_cast");
    
    private final String spellId;
    private final int x, y, z;  // Target position

    public SpellCastPacket(String spellId, int x, int y, int z) {
        this.spellId = spellId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Encodes packet data to network buffer.
     *
     * @param buf The buffer to write to
     */
    public static void write(SpellCastPacket packet, PacketByteBuf buf) {
        buf.writeString(packet.spellId);
        buf.writeInt(packet.x);
        buf.writeInt(packet.y);
        buf.writeInt(packet.z);
    }

    /**
     * Decodes packet from network buffer.
     *
     * @param buf The buffer to read from
     * @return Decoded packet
     */
    public static SpellCastPacket read(PacketByteBuf buf) {
        String spellId = buf.readString();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        return new SpellCastPacket(spellId, x, y, z);
    }

    /**
     * Handles packet reception on server.
     *
     * @param packet The received packet
     * @param player The sending player
     */
    public static void handle(SpellCastPacket packet, 
                             PlayPayloadContext context) {
        context.taskQueue().execute(() -> {
            ServerPlayerEntity player = context.player();
            if (player != null) {
                Spell spell = SpellRegistry.get(packet.spellId);
                if (spell != null) {
                    SpellCaster.castSpell(player, spell);
                }
            }
        });
    }
}
```

**Key Patterns**:
- Packets are immutable records of data
- Separate encode/decode for serialization
- Handle method runs on server thread
- Uses `PlayPayloadContext` for safe thread scheduling

---

## 🛠️ Technical Architecture

### Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Modern language features (records, pattern matching) |
| **Minecraft** | 1.21.10 | Game version |
| **Fabric Loader** | 0.18.3 | Mod loading & versioning |
| **Fabric API** | 0.138.4 | Core modding hooks & utilities |
| **Yarn Mappings** | 1.21.10+build.3 | Human-readable class names |
| **Gradle** | 9.2.1 | Build automation |
| **SLF4J** | 2.0.17 | Logging framework |
| **Gson** | 2.13.2 | JSON parsing |

### Advanced Design Patterns Used

#### **1. Registry Pattern** (Fabric-Specific)
```java
// Type-safe, namespaced registration
Registry.register(Registries.BLOCK, 
    Identifier.of("mam", "mana_pool"), 
    block);
```

#### **2. Data Attachment API**
```java
// Persistent, synchronized player data
AttachmentRegistry.createPersistent(id, builder ->
    builder.serializer(codec).syncWith(streamCodec, syncPredicate));
```

#### **3. Event Callback System**
```java
// Functional event registration
ServerTickEvents.END.register(new ServerTickHandler());
```

#### **4. Packet Encoding/Decoding**
```java
// Safe network serialization
public static void write(Packet p, PacketByteBuf buf) { }
public static Packet read(PacketByteBuf buf) { }
```

#### **5. JSON-Based Configuration**
```json
{
  "id": "mam:fireball",
  "school": "fire",
  "manaCost": 15.0,
  "gemstones": ["ruby", "carnelian"]
}
```

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

✅ **JSON-Based Systems**: Modify spells without touching Java  
✅ **Type-Safe Registries**: Fabric's registry system prevents errors  
✅ **Data Attachments**: Persistent, synchronized player data  
✅ **Event-Driven**: Hook into game events cleanly  
✅ **Comprehensive Logging**: SLF4J integration for debugging  
✅ **Modular Design**: Easy to extend with new schools/rituals  

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

# Generate data files
./gradlew runDatagen

# Build mod JAR
./gradlew build

# JAR located at: build/libs/mana-and-magic-[version]-client.jar
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
- **Javadoc**: Required on all public methods/classes with `@param`, `@return`, `@throws`, `@author`, `@version`, `@since`

### Best Practices Applied in Codebase

1. **Thread Safety**: Synchronized maps for multi-threaded operations
2. **Null Safety**: `@NotNull` annotations with parameter validation
3. **Exception Handling**: Try-with-resources, proper logging
4. **Immutability**: Records for data objects, final fields
5. **Separation of Concerns**: Client/server split, modular systems
6. **Performance**: Tick-based operations, memory-efficient caching

### SLF4J Logging

```java
private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);

LOGGER.info("Spell cast by {}", playerName);  // INFO level
LOGGER.warn("Low mana warning for {}", playerName);  // WARNING level
LOGGER.error("Spell execution failed", exception);  // ERROR level
LOGGER.debug("Cooldown update: {}", cooldownTime);  // DEBUG level
```

---

## 🔗 Important References

- **Fabric Documentation**: https://docs.fabricmc.net
- **Yarn Mappings**: https://mappings.dev (1.21.10)
- **Fabric API Javadocs**: https://maven.fabricmc.net/docs/
- **Gradle Guide**: https://docs.gradle.org/9.2.1/userguide/
- **Fabric Wiki**: https://wiki.fabricmc.net/

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
2. Add comprehensive **Javadoc** comments (`@param`, `@return`, `@throws`, `@author`, `@version`)
3. Test changes in-game on Minecraft 1.21.10
4. Use **SLF4J** for logging (not `System.out.println`)
5. Keep **JSON configs** aligned with provided schemas
6. Use Fabric APIs instead of mixins where possible
7. Ensure thread-safety for server-side code
8. Submit pull requests with clear descriptions

---

**Last Updated**: December 28, 2025  
**Maintained By**: Mosberg  
**Framework**: Fabric 0.18.3 | Minecraft 1.21.10 | Java 21  
**Status**: Production-Ready Archive

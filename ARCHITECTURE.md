# Mana and Magic - Java Architecture & Implementation Guide

## 🏗️ System Architecture Overview

This document provides an in-depth analysis of the Mana and Magic mod's Java code architecture, showing how all the files work together to create a cohesive magical system.

---

## 📊 Initialization & Startup Flow

### Step-by-Step Mod Loading Sequence

```
Minecraft Launch
    ↓
Fabric Loader reads fabric.mod.json
    ↓
Fabric discovers ModInitializer: ManaAndMagic
    ↓
ManaAndMagic.onInitialize() called (SERVER + CLIENT)
    ├─→ Phase 1: Register Content (Blocks, Items, Entities, Particles)
    │   └─→ Uses Registries.BLOCK, Registries.ITEM, etc.
    ├─→ Phase 2: Initialize Systems
    │   ├─→ SpellRegistry.init() - Loads spell JSONs
    │   ├─→ RitualRegistry.init() - Loads ritual JSONs
    │   ├─→ GemstoneRegistry.init() - Loads gemstone configs
    │   └─→ ManaPoolManager.init() - Sets up attachment registry
    ├─→ Phase 3: Register Event Handlers
    │   ├─→ ServerTickEvents.END.register(ServerTickHandler)
    │   ├─→ ServerLifecycleEvents.SERVER_STARTED.register(...)
    │   └─→ AttackEntityCallback.EVENT.register(...)
    └─→ Phase 4: Register Network Handlers
        ├─→ SpellCastPacket receiver
        ├─→ RitualActivatePacket receiver
        └─→ ManaUpdatePacket (client-side)

CLIENT-ONLY initialization (after server setup):
Fabric discovers ClientModInitializer: ManaAndMagicClient
    ↓
ManaAndMagicClient.onInitializeClient() called
    ├─→ Register Renderers (Entities, Block Entities, Particles)
    ├─→ Register Screens (ManaHUD, SpellBook, etc.)
    ├─→ Register Keybindings (Spell Cast, Open Menu, etc.)
    ├─→ Register Client Events (Tick, Screen Init)
    └─→ Register Client Packet Receivers (Mana updates, cooldowns)

Game Ready for Play!
```

### fabric.mod.json Configuration

```json
{
  "schemaVersion": 1,
  "id": "mam",
  "version": "1.0.0",
  "name": "Mana and Magic",
  "environment": "*",
  "entrypoints": {
    "main": ["dk.mosberg.mam.ManaAndMagic"],
    "client": ["dk.mosberg.mam.client.ManaAndMagicClient"]
  },
  "mixins": [
    "mam.mixins.json",
    {
      "config": "mam.client.mixins.json",
      "environment": "client"
    }
  ],
  "depends": {
    "fabricloader": ">=0.18.3",
    "fabric-api": ">=0.138.4",
    "minecraft": "1.21.10"
  }
}
```

---

## 🔄 Data Flow Diagrams

### Spell Casting Complete Flow

```
┌─────────────────────────────────────────────────────────────┐
│ CLIENT SIDE (Player's Computer)                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. Player Press 'R' (keybind)                              │
│  2. ClientTickHandler.onEndTick()                           │
│     ├─→ Detects keybind pressed                             │
│     └─→ Creates SpellCastPacket(spellId, targetPos)        │
│  3. Send packet via ClientPlayNetworking.send()            │
│     └─→ Packet queued for transmission                      │
│                                                              │
└─────────────────────────────────────────────────────────────┘
                         ↓ NETWORK ↓
┌─────────────────────────────────────────────────────────────┐
│ SERVER SIDE (Game World Logic)                              │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  4. SpellCastPacket.handle() receives packet               │
│     └─→ Runs on server thread via taskQueue                │
│                                                              │
│  5. SpellCaster.castSpell(player, spell) called            │
│     ├─→ validateSpell() - Check spell exists               │
│     ├─→ hasRequiredGemstones() - Count gem slots           │
│     │   ├─→ Check helmet ItemStack for gems               │
│     │   ├─→ Check chestplate ItemStack for gems            │
│     │   ├─→ Check leggings ItemStack for gems              │
│     │   └─→ Check boots ItemStack for gems                 │
│     ├─→ isOnCooldown() - Check cooldown map               │
│     │   └─→ SPELL_COOLDOWNS.get(playerUUID)               │
│     ├─→ ManaPoolManager.consumeMana()                      │
│     │   ├─→ player.getAttached(MANA_ATTACHMENT)            │
│     │   ├─→ Check currentMana >= spellManaCost            │
│     │   ├─→ Subtract mana                                  │
│     │   └─→ player.setAttached() AUTO-SYNCS TO CLIENT    │
│     ├─→ spell.execute() - Perform spell effect             │
│     │   ├─→ Calculate damage/effect                        │
│     │   ├─→ Apply to nearby entities                       │
│     │   └─→ Spawn particles                                │
│     └─→ applyCooldown() - Update cooldown timer            │
│         └─→ SPELL_COOLDOWNS.put(spell.getId(), now)        │
│                                                              │
│  6. Send ManaUpdatePacket to client (automatic via sync)   │
│                                                              │
└─────────────────────────────────────────────────────────────┘
                         ↓ NETWORK ↓
┌─────────────────────────────────────────────────────────────┐
│ CLIENT SIDE - Update Display                                │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  7. ManaUpdatePacket.handleClient() receives                │
│  8. Update ManaHudScreen with new values                    │
│  9. HUD renders updated mana bar                            │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Mana Regeneration Flow

```
┌──────────────────────────────────────────────────┐
│ SERVER - Tick-Based Regeneration                 │
├──────────────────────────────────────────────────┤
│                                                   │
│  Each Server Tick (50ms):                        │
│                                                   │
│  ServerTickHandler.onEndTick(server) called     │
│  ├─→ tickCounter++                              │
│  │                                               │
│  ├─→ IF (tickCounter % 5 == 0):  // Every 100ms │
│  │   └─→ FOR each ServerPlayerEntity:           │
│  │       └─→ ManaPoolManager.regenerateMana()   │
│  │           ├─→ Get mana attachment            │
│  │           │   └─→ player.getAttached(...)    │
│  │           ├─→ Calculate: regen = rate *      │
│  │           │   (ticksPassed / 20)             │
│  │           ├─→ Min mana = currentMana + regen│
│  │           ├─→ Cap at maxMana                 │
│  │           └─→ player.setAttached(newMana)    │
│  │               (AUTO SYNCS VIA STREAM CODEC)  │
│  │                                               │
│  └─→ IF (tickCounter % 20 == 0):  // Every 1s   │
│      └─→ Update ritual block entities           │
│                                                   │
└──────────────────────────────────────────────────┘
           ↓ AUTOMATIC SYNC (StreamCodec) ↓
┌──────────────────────────────────────────────────┐
│ CLIENT - Display Update                          │
├──────────────────────────────────────────────────┤
│                                                   │
│ ClientTickHandler receives synced mana data     │
│ ├─→ ManaHudScreen.updateManaBar(newMana)        │
│ └─→ Next render frame displays updated HUD      │
│                                                   │
└──────────────────────────────────────────────────┘
```

---

## 🗂️ Key File Interactions

### File Dependency Graph

```
ManaAndMagic.java (Entry Point)
├── depends on: ManaAndMagicConfig.java
├── depends on: SpellRegistry.java
│   └── loads: data/mam/spells/*.json
├── depends on: RitualRegistry.java
│   └── loads: data/mam/rituals/*.json
├── depends on: GemstoneRegistry.java
│   └── loads: data/mam/gemstones/*.json
├── depends on: ManaPoolManager.java
│   ├── uses: AttachmentRegistry (Fabric API)
│   └── manages: Player mana storage
├── depends on: ServerTickHandler.java
│   └── calls: ManaPoolManager.regenerateMana()
├── depends on: SpellCaster.java
│   ├── uses: ManaPoolManager.consumeMana()
│   ├── uses: GemstoneValidator
│   └── applies: Cooldowns (HashMap)
└── depends on: Network Packet Handlers
    ├── SpellCastPacket
    ├── RitualActivatePacket
    └── ManaUpdatePacket

ManaAndMagicClient.java (Client Entry Point)
├── depends on: ManaHudScreen.java
│   └── displays: Mana bar, cooldowns
├── depends on: SpellKeybinds.java
│   └── defines: Input mappings
├── depends on: ClientTickHandler.java
│   └── captures: Keybind presses
└── depends on: Network Packet Handlers (client-side)
    └── ManaUpdatePacket (client receiver)
```

---

## 🔌 Fabric API Integration Points

### 1. Registry System (Type-Safe Registration)

```java
// File: ManaAndMagic.java
// Pattern: Use Fabric's Registry for content
Registry.register(Registries.BLOCK, 
    Identifier.of(MOD_ID, "mana_pool"), 
    new ManaPoolBlock(...));
```

**What this provides:**
- Type-safe, namespaced registration
- Automatic network synchronization awareness
- Integration with world data saving
- Proper creative tab placement

### 2. Data Attachments (Player Data Storage)

```java
// File: ManaPoolManager.java
// Pattern: Persistent, synced player data
public static final AttachmentType<ManaData> MANA_ATTACHMENT =
    AttachmentRegistry.createPersistent(
        Identifier.of("mam", "mana_pool"),
        builder -> builder
            .serializer(ManaData.CODEC)
            .syncWith(ManaData.STREAM_CODEC, 
                AttachmentSyncPredicate.all())
    );
```

**What this provides:**
- Replaces old NBT system with typed data
- Automatic persistence to disk
- Automatic network synchronization
- Thread-safe operations

### 3. Event Callbacks (Game Hook Points)

```java
// File: ManaAndMagic.java
// Pattern: Register event listeners
ServerTickEvents.END.register(new ServerTickHandler());
```

**What this provides:**
- Clean, functional event registration
- No mixins needed for many hooks
- Multiple listeners can coexist
- Clear event ordering

### 4. Network Packets (Safe Serialization)

```java
// File: SpellCastPacket.java
// Pattern: Encode/decode for network transmission
public static void write(SpellCastPacket packet, PacketByteBuf buf) {
    buf.writeString(packet.spellId);
    buf.writeInt(packet.x);
}

public static SpellCastPacket read(PacketByteBuf buf) {
    return new SpellCastPacket(buf.readString(), buf.readInt(), ...);
}
```

**What this provides:**
- Safe, ordered packet delivery
- Thread-safe scheduling
- Automatic client/server routing
- Type-checked serialization

---

## 🎮 Game Loop Integration

### Where Each File Participates in Game Loop

```
┌─────────────────────────────────────────────────────────┐
│ MINECRAFT GAME LOOP (Every 50ms per tick)              │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ Input Phase                                             │
├─────────────────────────────────────────────────────────┤
│ ClientTickHandler.java → Captures keybind              │
│ └─→ Sends SpellCastPacket to server                    │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ Server Tick Phase                                       │
├─────────────────────────────────────────────────────────┤
│ ServerTickHandler.java → Main tick handler             │
│ ├─→ Every 5 ticks: ManaPoolManager regenerate         │
│ ├─→ Every 20 ticks: Update rituals                    │
│ └─→ Handle spell packets                              │
│     └─→ SpellCaster.java → Execute spell logic        │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ Entity Update Phase                                     │
├─────────────────────────────────────────────────────────┤
│ RitualBlockEntity.java → Tick ritual blocks            │
│ └─→ Check patterns, execute rituals                   │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ World Save Phase                                        │
├─────────────────────────────────────────────────────────┤
│ Attachments auto-save via ManaData.CODEC              │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ Network Sync Phase                                      │
├─────────────────────────────────────────────────────────┤
│ ManaPoolManager auto-sync via StreamCodec             │
│ └─→ Sends to all watching players                     │
└─────────────────────────────────────────────────────────┘
    ↓
┌─────────────────────────────────────────────────────────┐
│ Client Render Phase                                     │
├─────────────────────────────────────────────────────────┤
│ ManaHudScreen.java → Render HUD overlay               │
└─────────────────────────────────────────────────────────┘
```

---

## 🧮 JSON Data Loading Pipeline

```
Build Time:
  ManaAndMagicDataGenerator.java
  └─→ Generates *.json files
      └─→ assets/mam/lang/en_us.json
      └─→ data/mam/spells/fireball.json
      └─→ data/mam/rituals/summoning.json

Runtime:
  ManaAndMagic.onInitialize()
  ├─→ SpellRegistry.init()
  │   ├─→ Loads: data/mam/spells/*.json
  │   └─→ Parses with Gson
  │       ├─→ Creates Spell objects
  │       └─→ Registers in registry map
  ├─→ RitualRegistry.init()
  │   ├─→ Loads: data/mam/rituals/*.json
  │   └─→ Parses ritual definitions
  └─→ GemstoneRegistry.init()
      ├─→ Loads: data/mam/gemstones/*.json
      └─→ Creates Gemstone objects

In-Game Usage:
  SpellCaster.castSpell()
  └─→ SpellRegistry.get(spellId)
      └─→ Returns cached Spell object
          └─→ Uses JSON data (manaCost, gemstones, etc.)
```

---

## 🔐 Thread Safety Considerations

### Which Files Must Be Thread-Safe

| File | Thread Safety | Mechanism |
|------|---|---|
| `ManaPoolManager.java` | ✅ MUST BE | Uses Attachment API (thread-safe) |
| `SpellCaster.java` | ⚠️ PARTIAL | `synchronized HashMap` for cooldowns |
| `ServerTickHandler.java` | ✅ SAFE | Only runs on server thread |
| `ClientTickHandler.java` | ✅ SAFE | Only runs on client thread |
| `SpellRegistry.java` | ✅ SAFE | Immutable after init, read-only |
| Network Packet Handlers | ✅ SAFE | Fabric handles thread scheduling |

### Potential Race Conditions Avoided

```java
// WRONG - Not thread-safe
Map<String, Long> cooldowns = new HashMap<>();  // Race condition!

// CORRECT - Thread-safe
Map<String, Long> cooldowns = 
    Collections.synchronizedMap(new HashMap<>());

// BETTER - Only accessed from server thread
// (Fabric guarantees packet.handle() runs on server thread)
```

---

## 📦 Dependency Injection Pattern

The mod uses a **Service Locator** pattern for accessing shared systems:

```java
// In ManaAndMagic.java (WRONG - tight coupling)
SpellCaster.castSpell(player, spell);
// ↓ Better...

// In SpellCaster.java (Service Locator Pattern - USED)
ManaPoolManager.consumeMana(player, amount);
// ↓ Access via static registry methods

// Future: Could use DI framework
// SpellCaster caster = new SpellCaster(manaPool, spellRegistry);
```

**Current Architecture**: Stateless utilities with static registries
**Why**: Simplifies Fabric integration, matches Minecraft patterns

---

## 🧪 Testing Implications

### What Can Be Unit Tested

```java
// Testable - Pure functions
SpellCaster.validateSpell(spell)  // ✅

// Testable - Mocks can be injected
ManaPoolManager.calculateRegenAmount(time)  // ✅

// Hard to test - Minecraft dependencies
SpellCaster.castSpell(player, spell)  // ⚠️ (needs mock Player)
```

### What Requires Integration Testing

- Actual packet sending/receiving
- Entity interaction with spells
- Ritual pattern matching in world
- Persistent attachment data loading

---

## 🚀 Performance Optimization Applied

### 1. Tick-Based Operations (Not Real-Time)

```java
// WRONG - Every millisecond
player.getMana();  // Inefficient

// CORRECT - Every 5 ticks (100ms)
if (tickCounter % 5 == 0) {
    regenerateMana();
}
```

**Impact**: 95% less computation for mana regen

### 2. Lazy Loading

```java
// Spells loaded from JSON at init, cached thereafter
SpellRegistry.get(spellId);  // O(1) lookup
```

### 3. Synchronized Collections

```java
// Single synchronized map instead of locking entire system
Map<UUID, Map<String, Long>> cooldowns = 
    Collections.synchronizedMap(...);
```

### 4. Efficient Data Structures

```java
// HashMap for O(1) lookups
// Not ArrayList for O(n) searches
```

---

## 📋 Checklist for Adding New Features

### Adding a New Spell

```
1. Create spell JSON in data/mam/spells/my_spell.json
   ├─ Define id, school, manaCost, gemstones
   └─ Define components (projectile, damage, etc.)

2. Update ManaAndMagicDataGenerator.java
   └─ Add language entry for new spell

3. Register spell effect class if needed
   └─ Extend SpellEffect, implement execute()

4. Create keybinding if spell has specific input
   └─ Add to SpellKeybinds.java

5. Test in-game
   ├─ Cast spell with keybind
   ├─ Verify mana consumption
   ├─ Check cooldown
   └─ Inspect particle effects
```

### Adding a New Gemstone

```
1. Create gemstone JSON in data/mam/gemstones/my_gem.json
   ├─ Define id, rarity, school affinity
   ├─ Define power bonuses
   └─ Define texture path

2. Create texture image
   └─ assets/mam/textures/gemstone/my_gem.png

3. Update language file
   └─ data/assets/mam/lang/en_us.json

4. Test socket system
   └─ Can place in armor, applies bonuses
```

---

## 🔍 Debugging Guide

### Common Issues & Solutions

| Issue | Cause | File to Check | Fix |
|-------|-------|---|---|
| Mana not syncing | Attachment not registered | ManaPoolManager.java | Check MANA_ATTACHMENT creation |
| Spell not casting | Missing gemstone check | SpellCaster.java | Verify hasRequiredGemstones() |
| Cooldown infinite | Cooldown not cleared | SpellCaster.java | Check applyCooldown() registration |
| HUD not updating | Packet not received | ManaUpdatePacket.java | Verify handleClient() method |

### Logging for Debugging

```java
// Enable debug logging in logback configuration
LOGGER.debug("Spell cache size: {}", spellRegistry.size());
LOGGER.info("Player mana: {}", ManaPoolManager.getMana(player));
LOGGER.warn("Spell cooldown active for {}", spellId);
LOGGER.error("Failed to load spell from JSON", exception);
```

---

## 🎓 Learning Path

### To Understand This Codebase

1. **Start with**: `ManaAndMagic.java` - Understand initialization
2. **Then study**: `ManaPoolManager.java` - Learn Fabric Attachments
3. **Next learn**: `SpellCaster.java` - Understand spell casting flow
4. **Explore**: `ServerTickHandler.java` - See game loop integration
5. **Finally**: Network packets - Understand client/server sync

### Key Fabric Concepts to Master

- **Registries** - Type-safe registration system
- **Attachments** - Persistent entity data
- **Events** - Callback-based hooks
- **Networking** - Packet serialization
- **Mixins** - Code injection (minimal use here)

---

**Last Updated**: December 28, 2025  
**Maintained By**: Mosberg  
**For**: Fabric 0.18.3 | Minecraft 1.21.10 | Java 21
package dk.mosberg.mam.client;

import javax.swing.text.JTextComponent.KeyBinding;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import dk.mosberg.mam.client.registry.RegisterClientEvents;
import dk.mosberg.mam.client.registry.RegisterScreens;
import dk.mosberg.mam.network.ManaPacketHandler;

/**
 * Client-only initializer - NEVER loaded on servers!
 * Minecraft 1.21.10 - Fabric Split Sources
 * Only client code here (GUI, keybinds, rendering, etc.)
 */
@Environment(EnvType.CLIENT)
public class ManaAndMagicClient implements ClientModInitializer {
  private static final Logger LOGGER = LoggerFactory.getLogger("Mana & Magic [Client]");

  // Keybinding identifiers
  private static final String KEY_CAST = "key.mana_and_magic.cast_spell";
  private static final String KEY_RITUAL = "key.mana_and_magic.quick_ritual";
  private static final String KEY_TOGGLE = "key.mana_and_magic.toggle_mana";
  private static final String KEY_CODEX = "key.mana_and_magic.open_codex";
  private static final String KEY_SPEC = "key.mana_and_magic.specialization";

  // Actual keybinding objects
  public static final KeyBinding CAST_SPELL = KeyBindingHelper.registerKeyBinding(
      new KeyBinding(
          KEY_CAST,
          InputUtil.Type.KEYSYM,
          GLFW.GLFW_KEY_R,
          KeyBinding.Category.create(Identifier.of("mana_and_magic", "mana_and_magic"))));

  public static final KeyBinding QUICK_RITUAL = KeyBindingHelper.registerKeyBinding(
      new KeyBinding(
          KEY_RITUAL,
          InputUtil.Type.KEYSYM,
          GLFW.GLFW_KEY_V,
          KeyBinding.Category.create(Identifier.of("mana_and_magic", "mana_and_magic"))));

  public static final KeyBinding TOGGLE_MANA_BAR = KeyBindingHelper.registerKeyBinding(
      new KeyBinding(
          KEY_TOGGLE,
          InputUtil.Type.KEYSYM,
          GLFW.GLFW_KEY_B,
          KeyBinding.Category.create(Identifier.of("mana_and_magic", "mana_and_magic"))));

  public static final KeyBinding OPEN_CODEX = KeyBindingHelper.registerKeyBinding(
      new KeyBinding(
          KEY_CODEX,
          InputUtil.Type.KEYSYM,
          GLFW.GLFW_KEY_C,
          KeyBinding.Category.create(Identifier.of("mana_and_magic", "mana_and_magic"))));

  public static final KeyBinding SPECIALIZATION_MENU = KeyBindingHelper.registerKeyBinding(
      new KeyBinding(
          KEY_SPEC,
          InputUtil.Type.KEYSYM,
          GLFW.GLFW_KEY_N,
          KeyBinding.Category.create(Identifier.of("mana_and_magic", "mana_and_magic"))));

  @Override
  public void onInitializeClient() {
    LOGGER.info("🎨 Initializing Mana & Magic Client (1.21.10)");

    // Register event handlers
    RegisterClientEvents.register();

    // Register screens
    RegisterScreens.register();

    // Register keybindings
    registerKeybinds();

    // Initialize UI/HUD rendering
    initializeHUD();

    // Initialize particles
    initializeParticles();

    // Initialize renderers
    initializeRenderers();

    // Register client packet receivers
    ManaPacketHandler.registerClientPayloads();

    // Register tooltips
    initializeTooltips();

    LOGGER.info("✨ Mana & Magic Client initialized!");
  }

  private void initializeHUD() {
    // Register HUD overlay
    HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client.player != null && client.world != null) {
        ManaHudOverlay.render(drawContext);
        SpellWheelOverlay.render(drawContext);
        CooldownOverlay.render(drawContext);
        RitualOverlay.render(drawContext);
      }
    });

    LOGGER.debug("HUD renderers registered");
  }

  private void registerClientEvents() {
    // Tick event
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      ClientTickHandler.onClientTick();
      KeybindHandler.onTick();
    });

    // Screen event
    ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
      ScreenHandler.onScreenOpen(screen);
    });

    LOGGER.debug("Client events registered");
  }

  private void initializeParticles() {
    ModParticles.register();
    LOGGER.debug("Particles registered");
  }

  private void initializeRenderers() {
    // Entity renderers
    EntityRendererRegistry.register();

    // Block entity renderers
    BlockEntityRendererRegistry.register();

    // Item model overrides
    ItemModelRegistry.register();

    LOGGER.debug("Renderers registered");
  }

  private void initializeTooltips() {
    TooltipComponentRegistry.register();
    LOGGER.debug("Tooltips registered");
  }
}

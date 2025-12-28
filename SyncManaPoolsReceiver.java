// src/client/java/net/manamagic/client/network/SyncManaPoolsReceiver.java
package dk.mosberg.mam.client.network;

import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayPayloadHandler;

import dk.mosberg.mam.client.gui.overlay.ManaHudOverlay;
import net.manamagic.network.payload.SyncManaPoolsPayload;

@Environment(EnvType.CLIENT)
public class SyncManaPoolsReceiver implements PlayPayloadHandler<SyncManaPoolsPayload> {
  @Override
  public void receive(SyncManaPoolsPayload payload, ClientPlayNetworking.Context context) {
    // ✅ CLIENT-ONLY code - safe to use client APIs
    MinecraftClient client = MinecraftClient.getInstance();
    if (client.player != null) {
      ManaHudOverlay.updateMana(
          payload.getPersonal(),
          payload.getAura(),
          payload.getReserve());
    }
  }
}

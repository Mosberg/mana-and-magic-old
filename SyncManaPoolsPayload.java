// src/main/java/net/manamagic/network/payload/SyncManaPoolsPayload.java
package dk.mosberg.mam.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.PacketType;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;

import dk.mosberg.mam.ManaAndMagic;

public class SyncManaPoolsPayload implements FabricPacket {
  public static final PacketType<SyncManaPoolsPayload> TYPE = PacketType.create(ManaAndMagic.id("sync_mana_pools"),
      SyncManaPoolsPayload::new);

  private final float personal;
  private final float aura;
  private final float reserve;

  public SyncManaPoolsPayload(float personal, float aura, float reserve) {
    this.personal = personal;
    this.aura = aura;
    this.reserve = reserve;
  }

  public SyncManaPoolsPayload(PacketByteBuf buf) {
    this.personal = buf.readFloat();
    this.aura = buf.readFloat();
    this.reserve = buf.readFloat();
  }

  @Override
  public void write(PacketByteBuf buf) {
    buf.writeFloat(personal);
    buf.writeFloat(aura);
    buf.writeFloat(reserve);
  }

  @Override
  public PacketType<?> getType() {
    return TYPE;
  }

  public float getPersonal() {
    return personal;
  }

  public float getAura() {
    return aura;
  }

  public float getReserve() {
    return reserve;
  }
}

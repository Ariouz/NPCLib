package fr.ariouz.npclib;

import fr.ariouz.npclib.events.InteractNPCAction;
import fr.ariouz.npclib.events.PlayerInteractAtNPCEvent;
import fr.ariouz.npclib.npc.NPC;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class PacketReader {

    Player player;
    Channel channel;

    private NPCLib npcLib;

    public PacketReader(Player player, NPCLib npcLib) {
        this.player = player;
        this.npcLib = npcLib;
    }

    public void inject() {
        CraftPlayer cPlayer = (CraftPlayer) this.player;
        channel = cPlayer.getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext arg0, Packet<?> packet, List<Object> arg2) throws Exception {
                arg2.add(packet);
                readPacket(packet);
            }
        });
    }

    public void uninject() {
        if (channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }


    public void readPacket(Packet<?> packet) {
        if (packet instanceof PacketPlayInUseEntity) {
            int id = (Integer) getValue(packet, "a");

            for (NPC npc : npcLib.getNpcs()) {
                if (npc.getEntityID() == id) {
                    if (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")) {
                        Bukkit.getPluginManager().callEvent(new PlayerInteractAtNPCEvent(player, npc, InteractNPCAction.ATTACK){});
                    } else if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
                        Bukkit.getPluginManager().callEvent(new PlayerInteractAtNPCEvent(player, npc, InteractNPCAction.INTERACT){});
                    }
                }
            }

        }
    }


    public void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

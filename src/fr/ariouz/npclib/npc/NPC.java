package fr.ariouz.npclib.npc;

import com.mojang.authlib.GameProfile;
import fr.ariouz.npclib.NPCLib;
import fr.ariouz.npclib.Reflections;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class NPC extends Reflections {

    int entityID;
    Location location;
    GameProfile gameProfile;

    PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo();

    public NPC(String name, Location location){
        entityID = (int) Math.ceil(Math.random() * 1000) + 1000;
        gameProfile = new GameProfile(UUID.randomUUID(), name);
        this.location = location;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void equip(NPCSlot slot, ItemStack itemstack){
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        setValue(packet, "a", entityID);
        setValue(packet, "b", slot.getID());
        setValue(packet, "c", itemstack);

        sendPacket(packet);
    }

    void spawn(Player player, boolean tabList){
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

        setValue(packet, "a", entityID);
        setValue(packet, "b", gameProfile.getId());
        setValue(packet, "c", getFixLocation(location.getX()));
        setValue(packet, "d", getFixLocation(location.getY()));
        setValue(packet, "e", getFixLocation(location.getZ()));
        setValue(packet, "f", getFixRotation(location.getYaw()));
        setValue(packet, "g", getFixRotation(location.getPitch()));
        setValue(packet, "h", 0);
        DataWatcher w = new DataWatcher(null);
        w.a(6,(float)20);
        w.a(10,(byte)127);
        setValue(packet, "i", w);
        addToTabList();
        sendPacket(packet, player);
        teleport(player.getLocation());
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!tabList) removeFromTabList();
            }
        }.runTaskLater(NPCLib.getPlugin(), 20L);

    }

    public void teleport(Location location){
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        setValue(packet, "a", entityID);
        setValue(packet, "b", getFixLocation(location.getX()));
        setValue(packet, "c", getFixLocation(location.getY()));
        setValue(packet, "d", getFixLocation(location.getZ()));
        setValue(packet, "e", getFixRotation(location.getYaw()));
        setValue(packet, "f", getFixRotation(location.getPitch()));

        sendPacket(packet);
        headRotation(location.getYaw(), location.getPitch());
        this.location = location.clone();
    }

    private void headRotation(float yaw,float pitch){
        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));


        sendPacket(packet);
        sendPacket(packetHead);
    }

    void destroy(Player player){
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        removeFromTabList();
        sendPacket(packet, player);
    }

    public void addToTabList(){
        PacketPlayOutPlayerInfo.PlayerInfoData data = packetPlayOutPlayerInfo.new PlayerInfoData(gameProfile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packetPlayOutPlayerInfo, "b");
        players.add(data);

        setValue(packetPlayOutPlayerInfo, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packetPlayOutPlayerInfo, "b", players);

        sendPacket(packetPlayOutPlayerInfo);
    }

    public void removeFromTabList(){
        PacketPlayOutPlayerInfo.PlayerInfoData data = packetPlayOutPlayerInfo.new PlayerInfoData(gameProfile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packetPlayOutPlayerInfo, "b");
        players.add(data);

        setValue(packetPlayOutPlayerInfo, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packetPlayOutPlayerInfo, "b", players);

        sendPacket(packetPlayOutPlayerInfo);
    }

    public int getEntityID() {
        return entityID;
    }

    private int getFixLocation(double loc){
        return (int) MathHelper.floor(loc * 32.0D);
    }

    private byte getFixRotation(float rot){
        return (byte) ((int) (rot * 256.0F / 360.0F));
    }

}

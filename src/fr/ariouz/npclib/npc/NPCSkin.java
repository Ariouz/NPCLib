package fr.ariouz.npclib.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NPCSkin {

    private String value;
    private String signature;

    public void setSkin(GameProfile gameProfile){
        gameProfile.getProperties().put("textures", new Property("textures", this.value, this.signature));
    }

    public String getSignature() {
        return signature;
    }

    public String getValue() {
        return value;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void getSkinByPlayer(Player player){
        EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
        GameProfile profile = playerNMS.getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        this.value = property.getValue();
        this.signature = property.getSignature();
    }

    public void getSkinByPlayerName(String playerName){

    }

}

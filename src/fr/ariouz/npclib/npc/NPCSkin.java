package fr.ariouz.npclib.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
        try{
            URL url_0 = null;
            url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);

            InputStreamReader reader_0 = null;

            reader_0 = new InputStreamReader(url_0.openStream());

            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = null;

            url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");

            InputStreamReader reader_1 = null;

            reader_1 = new InputStreamReader(url_1.openStream());

            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            this.value = textureProperty.get("value").getAsString();
            this.signature = textureProperty.get("signature").getAsString();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

package fr.ariouz.npclib.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

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

}

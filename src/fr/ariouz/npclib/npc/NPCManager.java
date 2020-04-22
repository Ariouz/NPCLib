package fr.ariouz.npclib.npc;

import fr.ariouz.npclib.NPCLib;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NPCManager {

    private NPCLib npcLib;

    public NPCManager(NPCLib npcLib){
        this.npcLib = npcLib;
    }

    public void spawn(Player player, NPC npc, NPCSkin npcSkin, boolean tabList) {
        npcSkin.setSkin(npc.getGameProfile());
        npc.spawn(player, tabList);
        npcLib.getNpcs().add(npc);
    }

    public void destroy(Player player){
        ArrayList<NPC> npcs = new ArrayList<>(npcLib.getNpcs());

        for(NPC npc : npcs){
            if(npc != null){
                npc.destroy(player);
                npcLib.getNpcs().remove(npc);
            }
        }
    }

    public void destroy(Player player, NPC npc){
        if(npc != null){
            npc.destroy(player);
            npcLib.getNpcs().remove(npc);
        }
    }

}

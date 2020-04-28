package fr.ariouz.npclib;

import fr.ariouz.npclib.npc.NPC;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class NPCLib {

    private static ArrayList<NPC> npcs = new ArrayList<>();
    private static Plugin plugin;

    public NPCLib(Plugin plugin){
        this.plugin = plugin;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static ArrayList<NPC> getNpcs() {
        return npcs;
    }
    
    public static void setNpcs(ArrayList<NPC> npcs) {
        NPCLib.npcs = npcs;
    }

}

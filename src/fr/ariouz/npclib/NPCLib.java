package fr.ariouz.npclib;

import fr.ariouz.npclib.npc.NPC;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class NPCLib {

    private ArrayList<NPC> npcs = new ArrayList<>();
    private Plugin plugin;

    public NPCLib(Plugin plugin){
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

}

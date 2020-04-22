package fr.ariouz.npclib.events;

import fr.ariouz.npclib.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class PlayerInteractAtNPCEvent extends Event {

    private Player player;
    private NPC npc;
    private InteractNPCAction action;

    public PlayerInteractAtNPCEvent(Player player, NPC npc, InteractNPCAction action){
        this.player = player;
        this.npc = npc;
        this.action = action;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public NPC getNpc() {
        return npc;
    }

    public InteractNPCAction getAction() {
        return action;
    }
}

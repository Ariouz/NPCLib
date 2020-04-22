# Ariouz NPCLib - Create easy NPCS without dependencies

## Create an NPC and use the PlayerInteractAtNPCEvent event

### 1- First you need to shade the library in your source directory.

### 2- Create an instance of NPCLib class:

    private NPCLib npcLib;

    @Override
      public void onEnable() {
      npcLib = new NPCLib(this);
    }

### 3- Create the NPC

In your PlayerJoinEvent method, add this:

    NPCManager npcManager = new NPCManager(npcLib);
    NPC myNpc = new NPC("Hello my friend", location);
    NPCSkin myNpcSkin = new NPCSkin();
    myNpcSkin.setValue(skinValue);
    myNpcSkin.setSignature(skinSignature);
        
    npcManager.spawn(player, myNpc, myNpcSkin, true);


### 4- Destroy the NPC when player leaves the server:

In your PlayerQuitEvent method, add this:

    
    NPCManager npcManager = new NPCManager(npcLib);
    npcManager.destroy(player);

### 5- Use the PlayerInteractAtNPCEvent event:
First, you need to register the Player, in you PlayerJoinEvent, add this:

    PacketReader packetReader = new PacketReader(player, npcLib);
    packetReader.inject();


Then, you need to create the event method:

    @EventHandler
    public void onPlayerInteractAtNPC(PlayerInteractAtNPCEvent e){
        Player player = e.getPlayer();
        NPC npc = e.getNpc();
        InteractNPCAction action = e.getAction();
        Bukkit.broadcastMessage(player.getDisplayName() + " interacted on an NPC");
        Bukkit.broadcastMessage("npc name : " + npc.getGameProfile().getName());
        Bukkit.broadcastMessage("action: " + action.toString());
    }

And sure, register you event in you onEnable method:

    Bukkit.getPluginManager().registerEvents(eventClass, plugin);

### 6- Exemple code:

For simplicity i put all my code in one class:

    private NPCLib npcLib;

    @Override
    public void onEnable() {
        npcLib = new NPCLib(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        NPCManager npcManager = new NPCManager(npcLib);
        NPC myNpc = new NPC("Hello my friend", player.getLocation());
        NPCSkin myNpcSkin = new NPCSkin();
        myNpcSkin.setValue("ewogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJpZCIgOiAiNWZjOGUzYTFjNzQyNDEyMDg3M2I2YWIwNDc5OTI2YWUiLAogICAgICAidHlwZSIgOiAiU0tJTiIsCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ1YWIzNWFiMTJjYzcwYmUzMjMxMzQ5NjQxYTBkMTM1MmM2MTFjMTAyNGE5ZDMxN2U2NjNmOTIwMjVjOWVlOSIsCiAgICAgICJwcm9maWxlSWQiIDogIjdhMzIxMzM3NzVkZjQwYjRiYWQyMTljMjc5NmQ4NDVlIiwKICAgICAgInRleHR1cmVJZCIgOiAiZWQ1YWIzNWFiMTJjYzcwYmUzMjMxMzQ5NjQxYTBkMTM1MmM2MTFjMTAyNGE5ZDMxN2U2NjNmOTIwMjVjOWVlOSIKICAgIH0KICB9LAogICJza2luIiA6IHsKICAgICJpZCIgOiAiNWZjOGUzYTFjNzQyNDEyMDg3M2I2YWIwNDc5OTI2YWUiLAogICAgInR5cGUiIDogIlNLSU4iLAogICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lZDVhYjM1YWIxMmNjNzBiZTMyMzEzNDk2NDFhMGQxMzUyYzYxMWMxMDI0YTlkMzE3ZTY2M2Y5MjAyNWM5ZWU5IiwKICAgICJwcm9maWxlSWQiIDogIjdhMzIxMzM3NzVkZjQwYjRiYWQyMTljMjc5NmQ4NDVlIiwKICAgICJ0ZXh0dXJlSWQiIDogImVkNWFiMzVhYjEyY2M3MGJlMzIzMTM0OTY0MWEwZDEzNTJjNjExYzEwMjRhOWQzMTdlNjYzZjkyMDI1YzllZTkiCiAgfSwKICAiY2FwZSIgOiBudWxsCn0=");
        myNpcSkin.setSignature("G+NEL87e3ztnViyEeqSNJcVz/ARKN7iOaFpRlneK351dDnYz5gHL2fGCl9NxyVNZxeHJ+Kl0JXwkT9JdIxH/pM7q6YQU+mE/9J9xoVRGm/LZws5bFPz+t85JksMyqP+fk86rrWWcbjijtWpuV7na2+vRLUKC0DkuR/fQ7hogOAR6OFTSkkq6MoVrsINAhyLc44IRGAwhytIS4zoiqEiG8n0YvUk8SG9gA+lhp8eINbmKhOyFWUThf1x0vtj2pZnFhOFKxXyzVJW45g9D5xorPOmoFEUbqv34LjZrqZrqQ82Ows0eouhewNOzPEMzEySG8CWwuJCuYBQXfwtO+ous0t1JLRzb2gxkHRgSGBqWy+yRBWdT/XDGqyaeXPaTdRZW3u5NOcFCA1HnQ8UB3lVBWEq5I2zXjW34yULVRzP3C/5aVotoTTdNKIO+LlK9tufbNi5wh7CMBfzYYYE29MchSXcASOholY8fCdXaslcddYEt8PtWgOli37Hx2UbvGoRzTipsE9ZopIbkgogV9A83lm01WH7ED0HFeBLrixTvwk1oBvcOWGCSmcDGVkPjbd2PCuVJ6ds7H2zucz8FEtIN62wI9BcmnvHmSkcer1cLnzhEAakzAmbVuSQzKHoLlBZ11Sve0PwR9trDuEyy+B2Re1lcWKylUg0ebPSGIBSk9CA=");

        npcManager.spawn(player, myNpc, myNpcSkin, true);

        PacketReader pr = new PacketReader(player, npcLib);
        pr.inject();
        new BukkitRunnable() {
            @Override
            public void run() {
                myNpc.teleport(player.getLocation());
            }
        }.runTaskLater(this, 40L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        NPCManager npcManager = new NPCManager(npcLib);
        npcManager.destroy(player);
    }

    @EventHandler
    public void onPlayerInteractAtNPC(PlayerInteractAtNPCEvent e){
        Player player = e.getPlayer();
        NPC npc = e.getNpc();
        InteractNPCAction action = e.getAction();
        Bukkit.broadcastMessage(player.getDisplayName() + " interacted on an NPC");
        Bukkit.broadcastMessage("npc name : " + npc.getGameProfile().getName());
        Bukkit.broadcastMessage("action: " + action.toString());
    }


### And i got this:

![Image result](https://raw.githubusercontent.com/Ariouz/NPCLib/master/npc_readme.png)

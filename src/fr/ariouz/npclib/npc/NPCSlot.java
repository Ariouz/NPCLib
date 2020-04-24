package fr.ariouz.npclib.npc;

public enum  NPCSlot {

    MAIN_HAND(0),
    BOOTS(1),
    LEGGINGS(2),
    CHESTPLATE(3),
    HELMET(4);

    private int id;

    NPCSlot(int ID){
        this.id = ID;
    }

    public int getID() {
        return id;
    }

}

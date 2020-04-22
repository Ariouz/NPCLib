package fr.ariouz.npclib.npc;

public enum  NPCSlot {

    MAIN_HAND(0),
    BOOTS(1),
    LEGGINGS(2),
    CHESTPLATE(3),
    HELMET(4);

    private int ID;

    NPCSlot(int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

}

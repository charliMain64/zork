import java.util.*;

abstract class NPC {

    protected String name = "";

    NPC (String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    abstract String speak(); 
    abstract String trade();
    abstract ArrayList<Item> getInventory();
    abstract void addToInventory(Item i);
    abstract void clearInventory();

}

import java.util.HashSet;
import java.util.ArrayList;

class DropCommand extends Command {
    
    private String itemName; 

    DropCommand(String itemName) {
        this.itemName = itemName;
    }

    String execute() {
        GameState GS = GameState.instance();
        Room currentRoom = GS.getAdventurersCurrentRoom();
        Item item;
        
        ArrayList<Item> inv = GS.getInventory();
        HashSet<Item> addToRoom = new HashSet<Item>();
        //checks if all items are being dropped
        if (itemName.equals("all")) {

            if (inv == null || inv.isEmpty()) {
                return "There is nothing to drop.";
            }
            for (Item i : new ArrayList<>(inv)) {
                GS.removeFromInventory(i);
                addToRoom.add(i);
                System.out.println("-Dropped " + i.getPrimaryName());
            }
            for (Item i : addToRoom) {
                GS.addItemToRoom(i, currentRoom);
            }

            NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
            // If there is an AttackNPC in the room, apply damage
            if (currentNPC != null && currentNPC instanceof AttackNPC) {
                Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
                System.out.println(currentNPC.getName() + " strikes you!");
                            }
            
            return "\nAll items dropped";
        }

        //checks if item is inv
        try {
            item = GS.getItemFromInventoryNamed(itemName);
        } catch (NoItemException e) {
           return "You don't have a(n) " + itemName;
        }

        //drops item from inv & adds it to room
        GS.removeFromInventory(item);
        GS.addItemToRoom(item, currentRoom);
        
        NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
        // If there is an AttackNPC in the room, apply damage
        if (currentNPC != null && currentNPC instanceof AttackNPC) {
            Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
            System.out.println(currentNPC.getName() + " strikes you!");
        }
        return "Dropped " + itemName;
    
    }

}

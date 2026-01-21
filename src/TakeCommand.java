import java.util.*;

class TakeCommand extends Command {
    private String itemName;

    TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    String execute() {
        GameState GS = GameState.instance();
        Room currentRoom = GS.getAdventurersCurrentRoom();
        HashSet<Item> roomContents = currentRoom.getContents();
        HashSet<Item> removeFromRoom = new HashSet<Item>(); 

        //code for if all items are being taken
        if (itemName.equals("all")) { 
            String returnDesc = "";

            if (roomContents == null || roomContents.isEmpty()) {
                return "There is nothing to take.";
            }
            
            for (Item i : roomContents) {
                if (i.getWeight() + GS.getInventoryWeight() > 40) {
                    returnDesc += "\nWeight limit reached, can't take " + i.getPrimaryName();
                    break;
                }

                GS.addToInventory(i);
                removeFromRoom.add(i);
                returnDesc += "-" + i.getPrimaryName() + " taken\n";
            }

            for (Item i : removeFromRoom) {
                GS.removeItemFromRoom(i, currentRoom);
            }
            
            NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
            // If there is an AttackNPC in the room, apply damage
            if (currentNPC != null && currentNPC instanceof AttackNPC) {
                Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
                System.out.println(currentNPC.getName() + " strikes you!");
                            }
           return returnDesc;
        }

        // check if player already has in inventory
        ArrayList<Item> currInventory = GS.getInventory();
        if (currInventory != null) {
            for (Item item : currInventory) {
                if (item != null) {
                    if (item.getPrimaryName().equals(itemName) || item.goesBy(itemName)) {
                        return "You already have the " + itemName;
                    }
                }
            }
        }

        // check room for item
        HashSet<Item> currRoomContents = currentRoom.getContents();
        if (currRoomContents != null) {
            for (Item item : currRoomContents) {
                if (item != null) {
                    if (item.getPrimaryName().equals(itemName) || item.goesBy(itemName)) {
                        if (GS.getInventoryWeight() + item.getWeight() > 40) {
                           return "\nWeight limit reached, your load is too heavy.";
                        }
                        else {
                            GS.addToInventory(item);
                            GS.removeItemFromRoom(item, currentRoom);
                            
                            NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
                            // If there is an AttackNPC in the room, apply damage
                            if (currentNPC != null && currentNPC instanceof AttackNPC) {
                                Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
                                System.out.println(currentNPC.getName() + " strikes you!");
                            }
                            
                            return itemName + " taken";
                        }
                    }
                }
            }
        }
        
        
        return "Can't add " + itemName + " to inventory";
   }

}

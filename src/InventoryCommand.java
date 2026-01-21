import java.util.ArrayList;

class InventoryCommand extends Command {

     String execute() {
         ArrayList<Item> inv = GameState.instance().getInventory();
         String res = "";

         if (inv.isEmpty()) {
             return "You are empty-handed.";
         }
         
         res += "You are carrying:";

         for (Item item : inv) {
            res += "\n  -" + item.getPrimaryName() + " : " + item.getValue() + " credits";
         }

        return res;
     
     }
}

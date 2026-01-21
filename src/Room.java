
import java.util.*;

public class Room {

    private String name = null; 
    private String desc = ""; 
    private Hashtable<String, Exit> exits = null; 
    //npc
    
    public Room(String name){
        this.name = name;
        exits = new Hashtable<>();
    }

    public Room(Scanner scnr) throws NoRoomException {
        GameState GS = GameState.instance();
        exits = new Hashtable<>();
        this.name = scnr.nextLine(); 

        if (this.name.equals("===")) {
            throw new NoRoomException();
        }
        
        String lineAfterRoomName = scnr.nextLine();
        if (lineAfterRoomName.contains("Contents:")) {
            //get all items that the room has  
            String contentsString = lineAfterRoomName.split(" ")[1];
            //split items into array by ","
            String[] splitContentsString = contentsString.split(",");
            
            for (String s : splitContentsString) {
                // get item from the items hashtable in Dunegon class
                Item item = this.getItemNamed(s);
                // add item to the GameState "allRoomContents" Hashtable
                this.add(item);
            }
            
            String descPart = scnr.nextLine();
            while(!descPart.equals("---")) {
                this.desc += (descPart + " \n");
                descPart = scnr.nextLine();
            }
        }
        else {
            String descPart = lineAfterRoomName;           
            while(!descPart.equals("---")) {
                this.desc += (descPart + " \n");
                descPart = scnr.nextLine();
            }
        }
       
    }
    
    public String getName() {
        return this.name;
    }

    public void setDesc(String desc) {
        this.desc = desc; 
    }

    String describe() {
       GameState GS = GameState.instance();

       if (GS.hasBeenVisited(this)) {
           return name;
       }

       if (!GS.isVerboseMode() && !GS.hasBeenVisited(this))  {
        
           String description = name + "\n" + desc;
        
           NPC npc = GS.getNPCFromRoom(this);
           if (npc != null) {
               if (npc instanceof AttackNPC) {
                   description += "\n     ! !     " + npc.getName() + " attacks you!     ! !     \n";
               }
               else{
                   description += npc.getName() + " resides here.\n";
               }
           }

           if (this.getContents() != null && !this.getContents().isEmpty()) {
               for (Item item : this.getContents()) {
                   description += "\nThere is a " + item.getPrimaryName() + " here.";
               }
           }

           GS.visit(this);

           return description;
    
       }

       if (!GS.hasBeenVisited(this)) {
        
           String description = name + "\n" + desc;

           NPC npc = GS.getNPCFromRoom(this);
           if (npc != null) {
               if (npc instanceof AttackNPC) {
                   description += "\n     ! !     " + npc.getName() + " attacks you!     ! !     \n";
               } else {
                   description += npc.getName() + " resides here.\n";
               }
           }

           if (this.getContents() != null && !this.getContents().isEmpty()) {
               for (Item item : this.getContents()) {
                   description += "\nThere is a " + item.getPrimaryName() + " here.";
               }
           }

           description += "\nExits:";
           for (Exit exit : this.exits.values()) {
               description += "\n" + exit.describe();
           }

           GS.visit(this);
           return description;
       }
       return name;
    }

    String lookAtRoom() {
        String description = "\n"; 
        description += this.name + "\n";
        description += this.desc + "\n";
        
        // NPCs
        NPC npc = GameState.instance().getNPCFromRoom(this);
            
        if (npc != null) {
            if (npc instanceof AttackNPC) {
                description += "\n     ! !     " + npc.getName() + " attacks you!     ! !     \n";
            }
            else {
                description += "\n" + npc.getName() + " resides here.\n";
            }
        }


        if (this.getContents() != null) {
            for (Item item : this.getContents()) {
                description += "There is a " + item.getPrimaryName() + "\n";
            }
        }

        // check if verbose if on 
        if (GameState.instance().isVerboseMode()) {
        description += "Exits:";
        for (Exit exit : this.exits.values()) {
            description += "\n" + exit.describe();
        }
        
       }
        return description;
    }

    public void addExit(Exit exit) {
        exits.put(exit.getDir(), exit);
    }
    
    Room leaveBy(String dir) {
        if (exits.get(dir) != null) {
            return exits.get(dir).getDest();
        }
        else {
            return null;
        }
    }

    HashSet<Item> getContents() {
        return GameState.instance().getItemsInRoom(this);
    }

    Item getItemNamed(String name) {
        return GameState.instance().getDungeon().getItem(name);
    }

    void add(Item item) {
        GameState.instance().addItemToRoom(item, this);       
    }

    void remove(Item item){
        GameState.instance().removeItemFromRoom(item, this);       
    }

    public class NoRoomException extends Exception { }
    
 }

import java.util.*;
import java.io.*;

class GameState {

    private static GameState instance = null;
    private Dungeon dungeon = null;
    private Room currRoom = null; 
    private HashSet<Room> visited = null; 
    private ArrayList<Item> inventory = null; 
    private Hashtable<Room, HashSet<Item>> allRoomContents = null; 
    private Hashtable<Room, NPC> npcRoomPair = null;
    private Hashtable <String, NPC> npcNames = null;
    private ArrayList<NPC> deadNPCs = null;
    private boolean verbose = true;
    private Random rand = null;
    
    public static GameState instance() {
        if (GameState.instance == null) {
            GameState.instance = new GameState();
        } 
        return GameState.instance;
    }

    private GameState() {
        visited = new HashSet<>();
        inventory = new ArrayList<Item>();
        allRoomContents = new Hashtable<Room, HashSet<Item>>();
        npcRoomPair = new Hashtable<Room, NPC>();
        npcNames = new Hashtable<String, NPC>();
        deadNPCs = new ArrayList<NPC>();
        rand = new Random();
        rand.setSeed(13);
    }

    public Random getRand() {
        return this.rand;
    }
    
    public void setVerboseMode(boolean isVerbose) {
        this.verbose = isVerbose;
     }

    public boolean isVerboseMode() {
        return this.verbose;
    }

    public NPC getNPCByName(String name) {
        return npcNames.get(name);
    }
    public Room getNPCRoom(NPC targetNpc) {
//        NPC targetNpc = this.getNPCByName(name);
        for (Room room : npcRoomPair.keySet()) {
            if (npcRoomPair.get(room).equals(targetNpc)) {
                return room;
            }
        }
       return null; 

    }
    
    public Item getItemFromNPCInventory(String item) throws NoNPCItemException {

        for (Item i : npcRoomPair.get(this.currRoom).getInventory()) {
            if (i.getPrimaryName().equals(item) || i.goesBy(item)) {
               return i;
           }
        }

        throw new NoNPCItemException();
    }

    void addNPCtoRoom(Room room, NPC npc) {
        this.npcRoomPair.put(room, npc);
        this.npcNames.put(npc.getName(), npc);
    }

    public NPC getNPCFromRoom(Room room) {
        return this.npcRoomPair.get(room);
    }

    public ArrayList<NPC> getDeadNPCs() {
        return deadNPCs;
    }

    public void removeNPCFromRoom(Room room, NPC npc) {
        this.npcRoomPair.remove(room, npc);
        this.npcNames.remove(npc.getName(), npc);
    }
/*    public boolean hasAttackNPC(Room room) {
        if (getNPCFromRoom(AdventurersCurrentRoom)) {
            NPC npc = getNPCFromRoom
            if         
        }
    }
*/
    public void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Room getAdventurersCurrentRoom() {
        return this.currRoom;
    }

    public void setAdventurersCurrentRoom(Room room) {
        this.currRoom = room;
        // visit the curernt room in case teleport event
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public void setDungeon(Dungeon d) {
        this.dungeon = d;
    }

    void visit(Room room) {
        visited.add(room);
    }

    boolean hasBeenVisited(Room room) {
        return visited.contains(room);
    }

    ArrayList<Item> getInventory() { 
        return this.inventory;
    }

    int getInventoryWeight() {
        int weight = 0;

        for (Item item : this.inventory) {
           weight += item.getWeight(); 
        }

        return weight;
    }

    boolean isInPlayerInventory(Item item) {
        return this.inventory.contains(item);
    }


    void addToInventory(Item item) {
        this.inventory.add(item);
    }

    void removeFromInventory(Item item) {
        this.inventory.remove(item);
    }

    Item getItemInVicinityNamed(String name) throws NoItemException { 
        try {
            Item returnItem = this.getItemFromInventoryNamed(name);
            return returnItem;
        }
        catch(NoItemException e) {
            if (allRoomContents.get(this.currRoom) != null) { 
                for (Item item : allRoomContents.get(this.currRoom)) {
                    if (item != null) {
                        if (item.getPrimaryName().equals(name) || item.goesBy(name)) {
                            return item;
                        }
                    }
                }
            }
            throw new NoItemException();
        }
   }

    Item getItemFromInventoryNamed(String name) throws NoItemException {
        
        for (Item item : this.inventory) {
           if (item.getPrimaryName().equals(name) || item.goesBy(name)) {
               return item;
           }

        }

       throw new NoItemException();
    }

    HashSet<Item> getItemsInRoom(Room room) {
        return this.allRoomContents.get(room);
    }

    void addItemToRoom(Item item, Room room) {
        // room has nothing in it yet
        if (allRoomContents.get(room) == null) {
            HashSet<Item> itemsSet = new HashSet<Item>();
            itemsSet.add(item);
            allRoomContents.put(room, itemsSet); 
        }
        // other wise, add item to already existing hashset
        else {
            allRoomContents.get(room).add(item);
        }
    }

    void removeItemFromRoom(Item item, Room room) {
        this.allRoomContents.get(room).remove(item);
    }


    void store(String saveName) {
                   
        try {
            PrintWriter pw = new PrintWriter(new File(saveName));
            pw.println("Zork III save data");
            String path; 
            
            // get dungeon zork file's full path and store in save file
            try {
                File file = new File(this.dungeon.getFilename());
                path = file.getAbsolutePath();
                pw.println("Dungeon file: " + path);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
             
            pw.println("Room states:");
            for (Room room : visited) {
                pw.println(room.getName() + ":");
                pw.println("beenHere=true");
                
                HashSet<Item> roomContents = this.allRoomContents.get(room);
                if (roomContents != null && !roomContents.isEmpty()) {
                    pw.print("Contents: ");

                    // if works, remove last comma
                    for (Item item : roomContents) {
                        pw.print(item.getPrimaryName() + ",");
                    }
                    pw.println();
                }

                pw.println("---");

            }

            pw.println("===");
            pw.println("Adventurer:");
            pw.println("Current room: " + this.getAdventurersCurrentRoom().getName());
            pw.print("Inventory: ");
            for (Item item : this.inventory) {
                pw.print(item.getPrimaryName() + ",");
            }
            pw.println(); 
            pw.println("Health:" + Player.instance().getHealthInt());
            pw.println("Score:" + Player.instance().getScore());
            pw.println("Hunger:" + Player.instance().getHungerInt());
            pw.print("Used Score Items:");
            for (Item i : ScoreEvent.usedScoreItems) {
                pw.print(i.getPrimaryName() + ",");
            }
            pw.println();

            pw.println("===");
            pw.println("NPCS:");
            
           //saves dead enemies first 
            for (NPC attackNPC : this.deadNPCs) {
                pw.print(attackNPC.getName() + ":Dead");
                pw.println();
            } 
            for (Room room : this.visited) {
                NPC npc = this.npcRoomPair.get(room);
                
                if (npc != null) {
                    ArrayList<Item> npcItems = npc.getInventory();

                    if (!npcItems.isEmpty()) {
                        pw.print(npc.getName() + ":");

                        for (Item i : npc.getInventory()) {
                            pw.print(i.getPrimaryName() + ",");
                        }
                        pw.println();
                    }
                }

            }
            pw.println("===");
            pw.println("Verbose: " + 
                  Boolean.toString(GameState.instance().isVerboseMode()));


            pw.flush();
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void restore(String filename){
        Hashtable<Room, HashSet<Item>> updateRooms = new Hashtable<Room, HashSet<Item>>(); 
        try {
            //open save file w/ scanner
            File file = new File(filename);
            Scanner scnr = new Scanner(file);

            scnr.nextLine(); // throw out "zork III save data" line
                            
            File dungeonFile = new File(scnr.nextLine());
            this.setDungeon(new Dungeon(dungeonFile.getName()));
        
            scnr.nextLine(); //throw out "room states:" line
            
            while (true) {
                String roomName = scnr.nextLine();
                if (roomName.equals("===")) { break; }
                roomName = roomName.substring(0, roomName.length() - 1);

                Room visitedRoom = this.dungeon.getRoom(roomName);
                this.visit(visitedRoom);
                
                updateRooms.put(visitedRoom, new HashSet<Item>());

                scnr.nextLine(); // thow out "beenHere=true"
                
                String contents = scnr.nextLine();
            
                if (contents.contains("Contents:")) {
                    contents = contents.substring(contents.indexOf(":") + 2);
                    String[] contentsArray = contents.split(",");
                    
                    for (String item : contentsArray) {
                         Item currItem = this.dungeon.getItem(item);
                         updateRooms.get(visitedRoom).add(currItem);
                    }

                    scnr.nextLine(); // throw out "---":
                }
            }

            scnr.nextLine(); // throw out "Adventurer: "

            String currentRoom = scnr.nextLine();
            currentRoom = currentRoom.substring(currentRoom.indexOf(":") + 2);
            this.setAdventurersCurrentRoom(this.getDungeon().getRoom(currentRoom)); 

            String[] inventoryStr = scnr.nextLine().split(" "); 
            
            if (inventoryStr.length == 1) {
            }
            else {
                for (String string : inventoryStr[1].split(",")) {
                    Item item = this.dungeon.getItem(string);
                    this.addToInventory(item);
                }
            }

            int health = Integer.parseInt(scnr.nextLine().split(":")[1]);
            Player.instance().setInitHealth(health);
            int score = Integer.parseInt(scnr.nextLine().split(":")[1]);
            Player.instance().setInitScore(score);
            int hunger = Integer.parseInt(scnr.nextLine().split(":")[1]);
            Player.instance().setInitHunger(hunger);

            String[] scoreItems = scnr.nextLine().split(":");
            if (scoreItems.length != 1) {
                String scoreItemsStr = scoreItems[1];

                for (String itemName : scoreItemsStr.split(",")) {
                    Item item = this.dungeon.getItem(itemName);
                    ScoreEvent.usedScoreItems.add(item);
                }
            }

            scnr.nextLine(); // throw out === 
            scnr.nextLine(); // throw out NPCS: 
                                                 
            while (true) {
                String line = scnr.nextLine();

                if (line.equals("===")) {
                    break;
                }

                NPC npc = this.getNPCByName(line.split(":")[0]);
                
                //checks if npc is dead
                if (line.contains("Dead")) {
                    Room npcRoom = this.getNPCRoom(npc);
                    this.removeNPCFromRoom(npcRoom, npc);
                }
                npc.clearInventory();
                String npcInventoryString = line.split(":")[1];
            
                for (String string : npcInventoryString.split(",")) {
                    Item item = this.dungeon.getItem(string);
                    npc.addToInventory(item);
                }
            }
            String verboseString = scnr.nextLine().split(" ")[1];

            if (verboseString.equals("false")){
               this.setVerboseMode(false);
            }


            for (Map.Entry<Room, HashSet<Item>> roomContentsPair : updateRooms.entrySet()) {
                this.clearRoom(roomContentsPair.getKey()); //clears the room
                for (Item i : roomContentsPair.getValue()){
                    this.addItemToRoom(i, roomContentsPair.getKey()); 
                }
            }

        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    void clearRoom(Room room) {
        if (this.allRoomContents.get(room) != null && !this.allRoomContents.get(room).isEmpty()) {
            this.allRoomContents.get(room).clear();
        }
    }

    public class IllegalSaveFormatException extends Exception { }
}

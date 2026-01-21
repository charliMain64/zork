
import java.util.*;
import java.io.File;

public class Dungeon {
   
    private String title = "";
    private String filename = "";
    private Room entry = null; 
    private Hashtable<String, Room> rooms = new Hashtable<String, Room>();
    private Hashtable<String, Item> items = new Hashtable<String, Item>();

    public Dungeon(Room entry, String title) {
        this.title = title;
        this.entry = entry;
        this.add(entry);
    }

    public Dungeon (String filename) throws IllegalDungeonFormatException  {
        this.filename = filename;
        GameState GS = GameState.instance();
        

        // ----- Opening the file and making sure its in correct format -------
        try {
            File file = new File("../files/" + this.filename);
            Scanner scnr = new Scanner(file); 
            this.title = scnr.nextLine();
            String version =  scnr.nextLine();

            if (!version.equals("Zork++")) {
                throw new IllegalDungeonFormatException();
            }

            scnr.nextLine(); // skip "===" delimter
        // -----------------------------------------------------------------------
            // Initialize dungeon
            GS.initialize(this);
            
        // ------------------- Hydrate Item objects -----------------------------
            scnr.nextLine(); // skip "Items:" delimiter 

            try {
                while(true) {
                    Item item = new Item(scnr);
                    this.add(item);
                }
            }
            catch (NoItemException e) {
            }

        // -------------------- Hydrate Room Objects  -------------------------

            scnr.nextLine(); //skip "Rooms: " line
                             
            try {
                while (true) {
                    Room newRoom = new Room(scnr);

                    if (this.rooms.isEmpty()) {
                        this.entry = newRoom;
                    }

                    this.add(newRoom);

                }
            } catch (Room.NoRoomException e) {
            }

            // set current room in GameState to this.entry 
            // after all room objects have been made
            GS.setAdventurersCurrentRoom(this.entry);
           
      
         //----------------------Hydrate NPC Objects----------------------------
            scnr.nextLine(); //skip "NPCS: " line
                          
            try {
                while (true) {
                    NPC npc = NPCFactory.instance().parse(scnr);
                    scnr.nextLine(); // throw out "---" delimeter
                }
            } catch (NoNPCException e) {
            }

       

        // -------------------- Hydrate all exit objects ---------------------

            scnr.nextLine(); // throwing out "Exits: " line

            try {
                while (true) {
                   new Exit(scnr);
                }
            } 
            catch (Exit.NoExitException e) {
            }

        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // ------------------------------------------------------------------
    }

    public Room getEntry() { 
        return this.entry;  
    }
    
    public String getTitle() {
        return this.title;
    }

    public void add(Room room) {
        this.rooms.put(room.getName(), room);
    }

    public Room getRoom(String roomname) {
        return this.rooms.get(roomname);
    }

    public String getFilename() { return this.filename; }

    public Item getItem(String itemName) { 
        return this.items.get(itemName);
    }

    public HashSet<Item> getAllItems() {
        HashSet<Item> allItems = new HashSet<Item>();

        for(Item item : this.items.values()) {
            if (item != null) {
                allItems.add(item);
            }
        }

        return allItems;
    }

    public void add(Item item) {
        this.items.put(item.getPrimaryName(), item);
    }

    public Room getRandomRoom() {
        Random rand = GameState.instance().getRand();
        Enumeration<Room> values = rooms.elements();
        
        int size = rooms.size();
        int randomIndex = rand.nextInt(size);

        int currentIndex = 0;
        for (Room room; values.hasMoreElements(); currentIndex++) {
            room = values.nextElement();
            if (currentIndex == randomIndex) {
                return room;
            }
        }
        return null;
    }





    public class IllegalDungeonFormatException extends Exception { }
}

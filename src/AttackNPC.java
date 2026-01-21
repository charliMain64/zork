import java.util.*;

class AttackNPC extends NPC {
   
    private int health; 
    private int attack;
    
    private ArrayList<String> quotes = new ArrayList<String>();
    private ArrayList<String> responses = new ArrayList<String>(
            Arrays.asList("grunts", "steps back", "flinches", "winces", "staggers", "yelps"));
    
    Room NPCroom = null;

	AttackNPC(Scanner s) {
        super(s.nextLine());
       
        health = Integer.parseInt(s.nextLine().split(":")[1]);
        attack = Integer.parseInt(s.nextLine().split(":")[1]); 

        String lineAfterStats = s.nextLine();
        if (lineAfterStats.contains("Quotes:")) {
                String[] attackLines = lineAfterStats.split(":")[1].split(",");
            for (String line : attackLines) {
                this.quotes.add(line);
            }
            NPCroom = GameState.instance().getDungeon().getRoom(s.nextLine());
            GameState.instance().addNPCtoRoom(NPCroom, this);
        } else {
            NPCroom = GameState.instance().getDungeon().getRoom(lineAfterStats);
            GameState.instance().addNPCtoRoom(NPCroom, this);
        }
    }
    
    public int getNPCAttack() {
       return this.attack; 
    }

    public void setNPCHealth(int health) {
        Random rand = new Random();
        int randResponse = rand.nextInt(this.responses.size());
        int randQuote = rand.nextInt(this.quotes.size());

        this.health -= health;
        if (this.health <= 0) {
            System.out.println("\n" + this.name + " falls, vanquished forever.");
            GameState.instance().removeNPCFromRoom(NPCroom, this);
            GameState.instance().getDeadNPCs().add(this);   
        } else {
            System.out.println("\n" + this.name + " " + this.responses.get(randResponse) + " at your attack.");
            System.out.println("\"" + this.quotes.get(randQuote) + "!\""); 
        }
    }

    public int getNPCHealth(){
        return health;
    }

    //maybe add speaking
    String speak() {
        return this.name + " refuses to talk to you."; 
    }
    
    String trade() {
        return this.name + " ain't gonna trade with you.";
    }
    
    ArrayList<Item> getInventory() {
        return new ArrayList<Item>();
    }
    void addToInventory(Item i) {}
    void clearInventory() {}

}

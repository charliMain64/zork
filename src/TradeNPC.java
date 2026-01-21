import java.util.*;

class TradeNPC extends NPC {

	protected ArrayList<Item> inventory = new ArrayList<Item>(); 
    
	TradeNPC(Scanner s) {
        super(s.nextLine());

        for (String item : s.nextLine().split(":")[1].split(",")) {
            Item i = GameState.instance().getDungeon().getItem(item);
            this.inventory.add(i);
        }

        Room NPCroom = GameState.instance().getDungeon().getRoom(s.nextLine());
        GameState.instance().addNPCtoRoom(NPCroom, this);

	}

	ArrayList<Item> getInventory() { 
		return this.inventory; 
	}

    String speak() {
        return this.name + " ignores you.";
    }

	String trade() {
        GameState GS = GameState.instance();

        this.showInventory();
        this.showPlayerInventory();
        System.out.print("Trade what item in current inventory? (q to cancel and \",\" for multiple items) \n> ");
		Scanner scnr = new Scanner(System.in);
		String playerItemStr = scnr.nextLine();	

        if (playerItemStr.equals("q")) {
            return "\nTrade Cancelled";
        }

		try {
            ArrayList<Item> playerItems = new ArrayList<Item>();
            ArrayList<Item> npcItems = new ArrayList<Item>();

            for (String itemStr : playerItemStr.split(",")) {
                playerItems.add(GS.getItemFromInventoryNamed(itemStr));
            }

            if (playerItems.size() == 1) {
                System.out.print("Trade item for what? (q to cancel and \",\" for multiple items) \n> ");
            }
            else {
                System.out.print("Trade items for what? (q to cancel and \",\" for multiple items) \n> ");
            }

			String itemWantedStr = scnr.nextLine();
            
            if (itemWantedStr.equals("q")) {
                return "\nTrade Cancelled";
            }

            for (String itemStr : itemWantedStr.split(",")) {
                npcItems.add(GS.getItemFromNPCInventory(itemStr));
            }

            int sumVals = 0;
            for (Item i : playerItems) {
                sumVals += i.getValue();
            }

            int npcVals = 0;
            for (Item i : npcItems) {
                npcVals += i.getValue();
            }

            if (sumVals < npcVals) {
                return "\nNot enough credits.";
            }

            for (Item i : playerItems) {
                this.inventory.add(i);
                GS.removeFromInventory(i);
            }

            for (Item i : npcItems) {
                this.inventory.remove(i);
                GS.addToInventory(i);
            }

            return "\nTrade Successful!";
		}
		catch(NoItemException e) {
			return "You don't have that item in your inventory to trade";
		}
        catch(NoNPCItemException e) {
			return this.name + " doesn't have what you are looking for.";
		}       

	}

    void addToInventory(Item i) {
        this.inventory.add(i);
    }

	void showInventory() {
        String res = "-----" + this.name + "'s Inventory----\n";
        for (Item i : this.inventory)
        {
            res += "   -" + i.getPrimaryName() + " : " + i.getValue() + " credits" + "\n";
        }

        System.out.println(res);
    }

    void showPlayerInventory() {
        String res = "------Your Inventory-----\n";
        for (Item i : GameState.instance().getInventory())
        {
            res += "   -" + i.getPrimaryName() + " : " + i.getValue() + " credits" + "\n";
        }

        System.out.println(res);
    }

    void clearInventory() {
        this.inventory.clear();
    }
}

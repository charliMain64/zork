import java.util.*;

class NPCFactory {

	private static NPCFactory instance = null;
	
	public static NPCFactory instance() {
        if (NPCFactory.instance == null) {
            NPCFactory.instance = new NPCFactory();
        }
        return NPCFactory.instance;
    }

    private NPCFactory() {
    }
    
    NPC parse(Scanner scnr) throws NoNPCException {
		String npcType = scnr.nextLine();

		if (npcType.equals("===")) {
			throw new NoNPCException();
		}

		if (npcType.equals("verbal")) {
			return new VerbalNPC(scnr);
		}
		else if (npcType.equals("tradeable")) {
			return new TradeNPC(scnr);
		}
        else if (npcType.equals("enemy")) {
            return new AttackNPC(scnr);
        }
		else {
			return new VerbalTradeNPC(scnr);
		}
	}
}

import java.util.*;

class DamageEvent extends Event {

	private int changeInHealth = 0;

	DamageEvent(int damage) {	
		this.changeInHealth = damage;
	}	

	void execute() {
        NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom()); 
      
        if (currentNPC != null && currentNPC instanceof AttackNPC) { 
            ((AttackNPC) currentNPC).setNPCHealth(changeInHealth);
        }
    }

    void execute(String message) {
        
        NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom()); 
      
        if (currentNPC != null && currentNPC instanceof AttackNPC) { 
            System.out.println(message);
            ((AttackNPC) currentNPC).setNPCHealth(changeInHealth);
        }
        else {
            System.out.println(message);
        }
    }
}

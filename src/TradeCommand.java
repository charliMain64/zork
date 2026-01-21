import java.util.*;

class TradeCommand extends Command {

	String execute() {
		GameState GS = GameState.instance();

		//get npc from room and show all item along with the cost of each

		NPC npc = GS.getNPCFromRoom(GS.getAdventurersCurrentRoom());

        if (npc == null) {
            return "Who are you trying to trade with?";
        }

        if (npc instanceof AttackNPC) {
            Player.instance().setHealth(((AttackNPC) npc).getNPCAttack());
            System.out.println(npc.getName() + " strikes you!");
            return npc.trade();
        }
            
        return npc.trade();
        
	}

}

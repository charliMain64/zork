class SpeakCommand extends Command {
	String execute() {
	    NPC npc = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
        if (npc != null) {
            if (npc instanceof AttackNPC) {
                Player.instance().setHealth(((AttackNPC) npc).getNPCAttack());
                System.out.println(npc.getName() + " strikes you!");
                return npc.speak();
            }
            else {
                return npc.speak();
            }
        }
        else {
           return "Who are you trying to talk to?";
        }
	}
}

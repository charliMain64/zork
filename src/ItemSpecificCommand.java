class ItemSpecificCommand extends Command {

    private Item item = null;
    private String verb = "";

    ItemSpecificCommand (Item item, String verb) {
        this.item = item;
        this.verb = verb;
    }

    String execute() {
        String message = item.getMessageForVerb(verb);

        for (String s : this.item.getEvents(verb)) {
            Event e = EventFactory.instance().parseEvent(this.item, s);

            if (e instanceof WinEvent) {
                ((WinEvent) e).execute(message);
            }
            else if (e instanceof DieEvent) {
                ((DieEvent) e).execute(message);
            }
            else if (e instanceof DamageEvent) {
                ((DamageEvent) e).execute(message);
                return "";
            }
            else {
                e.execute();
            }
        }

        if (message == null) { 
            return "Cannot " + this.verb + this.item.getPrimaryName(); 
        }
        
        NPC currentNPC = GameState.instance().getNPCFromRoom(GameState.instance().getAdventurersCurrentRoom());
        // If there is an AttackNPC in the room, apply damage
        if (currentNPC != null && currentNPC instanceof AttackNPC) {
            Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
            System.out.println(currentNPC.getName() + " strikes you!");
        }
        return message; 
    }

}

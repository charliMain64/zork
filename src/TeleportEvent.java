class TeleportEvent extends Event {

	void execute() {
	    Room randomRoom = GameState.instance().getDungeon().getRandomRoom();

        GameState.instance().setAdventurersCurrentRoom(randomRoom); 
    }
}

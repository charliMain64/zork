class DisappearEvent extends Event {
	private Item item = null;

	DisappearEvent(Item item) {
		this.item = item;
	}
	
	void execute() {
        GameState.instance().removeFromInventory(item);
        GameState.instance().removeItemFromRoom(item, GameState.instance().getAdventurersCurrentRoom());
        this.item = null;
    
    }
}

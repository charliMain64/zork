class DropEvent extends Event {
	private Item item = null;
  // private Room room;
	DropEvent(Item item) {
		this.item = item;
    //  this.room = room;
	}
	
	void execute() {
      //System.out.println("made new DropEvent() and now executing");
		GameState.instance().removeFromInventory(item);
		GameState.instance().addItemToRoom(this.item, GameState.instance().getAdventurersCurrentRoom());
     // GameState.instance().getAdventurersCurrentRoom().addItemToRoom(item);
	}
}

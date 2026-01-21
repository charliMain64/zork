
class TransformEvent extends Event {
	private Item originalItem = null;
    private Item newItem = null;

	TransformEvent(Item originalItem, Item newItem) {
		this.originalItem = originalItem;
        this.newItem = newItem;
	}
    
	void execute() {

        GameState GS = GameState.instance();
        boolean isInInventory = GS.isInPlayerInventory(this.originalItem);

        if (!isInInventory) {
            GS.removeItemFromRoom(originalItem, GS.getAdventurersCurrentRoom());
            GS.addItemToRoom(this.newItem, GS.getAdventurersCurrentRoom());
        }
        else {
            GS.removeFromInventory(this.originalItem);
            GS.addToInventory(this.newItem);
        }

	}
	
}

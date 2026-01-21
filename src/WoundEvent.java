class WoundEvent extends Event {
	// a negative changeInHealth heals the player

	private int changeInHealth = 0;

	WoundEvent(int health) {	
		this.changeInHealth = health;
	}	

	void execute() {
        Player.instance().setHealth(changeInHealth);
	}
}

class EatEvent extends Event {
    private int changeInHunger = 0;

    EatEvent(int hunger) {
        this.changeInHunger = hunger;
    } 
    
    void execute() {
        Player.instance().setHunger(changeInHunger); 
    }

}


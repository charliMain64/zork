import java.util.*;

class ScoreEvent extends Event {
   private int changeInScore = 0;
   private Item item; 
   protected static ArrayList<Item> usedScoreItems = new ArrayList<Item>();
   
	ScoreEvent(int score, Item item) {	
		this.changeInScore = score;
        this.item = item;
	}	

	void execute() {
        if (!usedScoreItems.contains(this.item)) {
            Player.instance().setScore(changeInScore);
            usedScoreItems.add(this.item);
        }
    }

}

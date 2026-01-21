class ScoreCommand extends Command{
	ScoreCommand() {
	}

	String execute() {
        int score = Player.instance().getScore();
        //String scoreString =  String.valueOf(score);

        if (score <= 0) {
            return "Score: " + score + "    Rank: Amateur";
        }
        else if (score <= 1) {
            return "Score: " + score + "    Rank: Rookie";
        }
        else if (score <= 2) {
            return "Score: " + score + "    Rank: Novice";
        }
        else if (score <= 3) {
            return "Score: " + score + "    Rank: Intermidiate";
        }
        else if (score <= 4) {
            return "Score: " + score + "    Rank: Expert";
        }
        else {
            return "Score: " + score + "    Rank: Master";
        }
	}

}


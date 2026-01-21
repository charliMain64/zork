class Player {

	private static Player instance = null;
	private static int score = 0;
	private static int health = 10;
	private static int hunger = 20;
	
	public static Player instance() {
		if (Player.instance == null) {
					Player.instance = new Player();
		}
		return Player.instance;
    }

	private Player() {
	}

	public void setScore (int score) {
		this.score += score;
	}

    public void setInitScore(int score) {
        this.score = score;
    }

    // for restoring game
    public void setInitHealth(int health) {
        this.health = health;
    }

	public void setHealth (int health) {
		this.health -= health;
        // check if health is 0
        if (this.health <= 0) { 
            new DieEvent().execute();
        }
	}

	public int getScore () {
        return this.score;
	}

    // for saving game
    public int getHealthInt() {
        return this.health;
    }

	public String getHealth() {
        if (this.health <= 1) {
            return "You are about to die!";
        }
        else if (this.health <= 2) {
            return "Please heal yourself";
        }
        else if (this.health <= 3) {
            return "You're struggling to do basic things";
        }
        else if (this.health <= 4) {
            return "You're getting slower. Maybe heal up a little.";
        }
        else if (this.health <=5) {
            return "You're pretty tired and worn out, get some rest.";
        }
        else if (this.health <= 6) {
            return "You're starting to feel the effects of the dungeon.";
        }
        else if (this.health <= 7) {
            return "Still going strong but strain is setting in.";
        }
        else if (this.health <= 8) {
            return "Still going strong.";
        }
        else if (this.health <=9) {
            return "Tis but a scratch.";
        }
        else {
            return "You're as fit as can be.";
        }
	}
   
    public void setInitHunger(int hunger) {
        this.hunger = hunger;
    }

	public void setHunger(int hunger) {
		this.hunger -= hunger;
		// check if hunger < 0
        if (this.hunger == 1) {
            System.out.println("\n    ! !    You are starving to death!    ! !    ");
        }
        if (this.hunger <= 0) {
            new DieEvent().execute();
        }
	}

	public int getHungerInt() {
		return this.hunger;
	}

    public String getHunger() {
        if (this.hunger <= 1) {
            return "You're starving to death!";
        }
        if (this.hunger <= 3) {
            return "You're beginning to starve!";
        }
        if (this.hunger <= 5) {
            return "Eat something, man!";
        }
        if (this.hunger <= 10) {
            return "You're getting pretty hungry.";
        }
    
        if (this.hunger <= 15) {
            return "You could eat.";
        }

        if (this.hunger <= 20) {
            return "Fat, dumb, & happy.";
        }
        if (this.hunger > 20) {
            return "You're bloated, man.";
        }
    return null;
    }
}

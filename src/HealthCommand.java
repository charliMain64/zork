class HealthCommand extends Command{
	HealthCommand() {
	}

	String execute() {
		return Player.instance().getHealth();
	}
}

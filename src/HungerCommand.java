class HungerCommand extends Command {
    HungerCommand() {
    }

    String execute() {
        return Player.instance().getHunger(); 
    }
}

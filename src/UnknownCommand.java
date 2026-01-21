class UnknownCommand extends Command {
    private String bogusCommandString;

    UnknownCommand(String cmd) {
        this.bogusCommandString = cmd;
    }

    String execute() {
        return this.bogusCommandString + " is not a valid command.";
    }
}

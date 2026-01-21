class VerboseCommand extends Command{

    private String status = "";
   
    VerboseCommand(String status) {
        this.status = status;
    }

    String execute() {

        if (this.status.equals("on")) {
            GameState.instance().setVerboseMode(true);
            return "Verbose Mode ON";
        }
        else {
            GameState.instance().setVerboseMode(false);
            return "Verbose Mode OFF";
        }

        /*
          if (GameState.instance().isVerboseMode()) {  
            return "Verbose Mode ON";
        } else {  
            return "Verbose Mode OFF";
        }
        */
    }
}

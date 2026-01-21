import java.util.HashSet;

class LookCommand extends Command {
        
    String execute() {

       return GameState.instance().getAdventurersCurrentRoom().lookAtRoom();
    
   
    }

}

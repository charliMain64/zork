import java.util.Scanner;

class MovementCommand extends Command {
    private String dir; 

    MovementCommand(String dir) {
        this.dir = dir;
    }

    String execute() {
        GameState GS = GameState.instance();
        //creating the instance of the current 

        Room currRoom = GS.getAdventurersCurrentRoom();
        if (currRoom.leaveBy(this.dir) != null) {
           
           NPC currentNPC = GS.getNPCFromRoom(currRoom);
           
           //this locks players in the room until the enemy is dead
           if (currentNPC != null && currentNPC instanceof AttackNPC) {
               while (((AttackNPC) currentNPC).getNPCHealth() > 0) {
                   return currentNPC.getName() + " prevents you from leaving!";
               }
           } 
            
           GS.setAdventurersCurrentRoom(currRoom.leaveBy(this.dir));
           
          //player loses 1 hunger every successsful movement 
           Player.instance().setHunger(1);
            
           // If there is an AttackNPC in the room, apply damage
            if (currentNPC != null && currentNPC instanceof AttackNPC) {
                Player.instance().setHealth(((AttackNPC) currentNPC).getNPCAttack());
                System.out.println(currentNPC.getName() + " strikes you!");
            }
           
            return "\n" + GS.getAdventurersCurrentRoom().describe();
        }
        else {
            return "cannot go " + dir;
        }
    }

}



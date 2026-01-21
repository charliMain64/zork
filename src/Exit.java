import java.util.Scanner;

public class Exit {

    private String dir = null; 
    private Room src = null;
    private Room dest = null;

    public Exit(String dir, Room src, Room dest) {
        this.dir = dir;
        this.src = src;
        this.dest = dest;
    }

    public Exit (Scanner scnr) throws NoExitException {
        //GameState GS = GameState.instance();
        Dungeon DUNGEON = GameState.instance().getDungeon();

        String sourceRoom = scnr.nextLine();
        if (sourceRoom.equals("===")) {
            throw new NoExitException();
        }

        this.src = DUNGEON.getRoom(sourceRoom);
        this.dir = scnr.nextLine();
        this.dest = DUNGEON.getRoom(scnr.nextLine());
        //String destRoom = scnr.nextLine();

        scnr.nextLine(); // throw out '---' delimter
        
        this.src.addExit(this);
        
    }

    String describe() {
        return "You can go " + dir + " to " + dest.getName();
    }

    public String getDir() {
        return this.dir;
    }

    public Room getSrc() {
        return this.src;
    }

    public Room getDest() {
        return this.dest;
    }

    public class NoExitException extends Exception { }
    
}

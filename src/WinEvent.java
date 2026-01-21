import java.util.Arrays;

class WinEvent extends Event {

  void execute() {}

	void execute(String message) {
        System.out.println("\n" + message);
	    System.out.println("\n======= V I C T O R Y  A C H I E V E D ======\n");
        System.out.println("         " + new ScoreCommand().execute());
        System.exit(0);
    }
    
}

import java.util.Scanner;

class SaveCommand extends Command {
    private String saveFilename = "";
    
    SaveCommand() {
    }

    String execute() {
        Scanner scnr = new Scanner(System.in);
        do {
            System.out.println("Enter save file name: ");
            this.saveFilename = scnr.nextLine();     
        } while (this.saveFilename.equals(""));

        GameState.instance().store(this.saveFilename + ".sav");

        return "\ngame saved successfully";
    }
}

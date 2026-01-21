
import java.util.*;

class CommandFactory {
    
    private static CommandFactory instance = null;

    public static CommandFactory instance() {
        if (CommandFactory.instance == null) {
            CommandFactory.instance = new CommandFactory();
        }
        return CommandFactory.instance;
    }

    private CommandFactory() {
    }

    Command parse(String commandString) {
        // check for new commands : Speak,Trade
        String[] commandParts = commandString.split(" "); 
        String command = commandParts[0];
       
        HashSet<String> movementCommands= new HashSet<>(
                List.of("n", "e", "s", "w", "u", "d") 
            );

        if (movementCommands.contains(command)) { 
            return new MovementCommand(command);
        }
        else if (command.equals("take")) {
            try {
                if (commandParts.length > 1) {
                    String itemName = commandParts[1];
                    return new TakeCommand(itemName);
                } else {
                    throw new NoItemException();
                }
            }
            catch (NoItemException e) {
                System.out.println(command + " what?");
                return null;
            }
        }
        else if (command.equals("drop")) {
            try {
                if (commandParts.length > 1) {
                    String itemName = commandParts[1];
                    return new DropCommand(itemName);
                } else {
                    throw new NoItemException();
                }
            }
            catch (NoItemException e) {
                System.out.println(command + " what?");
                return null;
            }
        }
        else if (command.equals("look")) {
           return new LookCommand();
        }
        else if (command.equals("i") || command.equals("inventory")) {
           return new InventoryCommand();
        }
        else if (command.equals("save")) {
            return new SaveCommand();
        }
        else if (command.equals("score")) {
          return new ScoreCommand();
        }
        else if (command.equals("health")){
           return new HealthCommand();
        } 
        else if (command.equals("speak")) {
            return new SpeakCommand();
        }
        else if (command.equals("hunger")) {
            return new HungerCommand();
        }
        else if (command.equals("trade")) {
           return new TradeCommand();
        }
//        else if (command.equals("verbose on")) {
  //         GameState.instance().setVerboseMode(true);
    //       return new VerboseCommand();  
      //  }
        else if (command.equals("verbose")) {
            if (commandParts[1].equals("on") ||commandParts[1].equals("off")) {
                return new VerboseCommand(commandParts[1]);
            }
            else {
                return new UnknownCommand(commandString);
            }

            /*
           if (commandParts[1].equals("on")){
              GameState.instance().setVerboseMode(true);
              return new VerboseCommand();  
           }else{
              GameState.instance().setVerboseMode(false);
              return new VerboseCommand();
           }
           */
        }
 
        else {
            if (commandParts.length == 1) {
                return new UnknownCommand(commandString);
            }

            try {
                String verb = commandParts[0];
                String itemName = commandParts[1];
                Item item = GameState.instance().getItemInVicinityNamed(itemName);
                return new ItemSpecificCommand(item, verb);
            }
            catch(NoItemException e) {
                return new UnknownCommand(commandString);
            }
        }

    }
}

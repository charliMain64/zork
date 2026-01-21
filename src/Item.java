import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.FileReader;
import java.util.*;

public class Item {
    
    private String primaryName = ""; 
    private int weight ;
    private Hashtable<String,String> messages = new Hashtable<String, String>();
    private Hashtable<String,ArrayList<String>> events = new Hashtable<String, ArrayList<String>>();
    private int value = 0;
    // drink : [Transform(emptyCan),Wound(-1)]
    
    // make a value inst var
    // make method to get the value of item
    private HashSet<String> aliases = new HashSet<String>();

    public Item(Scanner scnr) throws NoItemException{

        String itemNames = scnr.nextLine();

        if (itemNames.equals("===")){
            throw new NoItemException();
        }
        
        String[] splitNames = itemNames.split(",");
        this.primaryName = splitNames[0];
        
        for(int i = 1; i < splitNames.length; i++) {
            this.aliases.add(splitNames[i]);
        }
        
        String itemWeight = scnr.nextLine();
        this.weight = Integer.parseInt(itemWeight);

        this.value = Integer.parseInt(scnr.nextLine().split(":")[1]);

        String itemAction;
        boolean possibleAction = false; //used to verify if item is able to enact action
        while(scnr.hasNextLine() && !(itemAction = scnr.nextLine().trim()).equals("---"))
        {
            possibleAction = true;
            String[] splitAction = itemAction.split(":");

            // if action has any events with it
            if (splitAction[0].indexOf("[") != -1) {
                int startIdx = splitAction[0].indexOf("[") + 1;
                int endIdx = splitAction[0].indexOf("]");
                String eventsString = splitAction[0].substring(startIdx, endIdx);
                String[] eventsArray = eventsString.split(","); 

                String action = splitAction[0].substring(0, splitAction[0].indexOf("["));
                String response = splitAction[1];
                messages.put(action, response); 
                this.events.put(action, new ArrayList<String>(Arrays.asList(eventsArray)));
            }
            // no events on the action
            else {
                String action = splitAction[0];
                String response = splitAction[1]; 
                messages.put(action, response); 
            }
        }
	}

    public int getValue() {
        return this.value;
    }

    public ArrayList<String> getEvents(String action) {
        if (this.events.get(action) == null) {
            return new ArrayList<String>();
        }
        return this.events.get(action);
    }

	public boolean goesBy(String name) {
		return aliases.contains(name);
	}

	public String getPrimaryName() {
		return this.primaryName;
	}
	public String getMessageForVerb(String verb) {
		return messages.get(verb);
	}
    
    public HashSet<String> getAllItemActions() {
        HashSet<String> actions = new HashSet<String>();

        for (String action : this.messages.keySet()) {
            if (action != null) {
                actions.add(action);
            }
        }

        return actions;
    }

	public String toString() {
		return "Item: " + primaryName + "'s weight is " + weight;
	}

	public int getWeight() {
		return weight;
	}

}


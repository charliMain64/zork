class EventFactory {

	private static EventFactory instance = null;
	
	public static EventFactory instance() {
        if (EventFactory.instance == null) {
            EventFactory.instance = new EventFactory();
        }
        return EventFactory.instance;
    }

    private EventFactory() {
    }

    
	Event parseEvent(Item item, String eventString) {

        if (eventString.equals("Die")) {
            return new DieEvent();
        }
        else if (eventString.equals("Disappear")) {
            return new DisappearEvent(item);
        }
        else if (eventString.equals("Drop")) {
            return new DropEvent(item);
        }
        else if (eventString.equals("Teleport")) {
            return new TeleportEvent();
        }
        else if (eventString.equals("Win")) {
            return new WinEvent();
        }
        else {
            // events with params
            if(eventString.indexOf("Score") != -1) {
                int startIdx = eventString.indexOf("(") + 1;
                int endIdx = eventString.indexOf(")");
                int param = Integer.parseInt(eventString.substring(startIdx, endIdx));
                return new ScoreEvent(param, item);
            }
            else if (eventString.indexOf("Transform") != -1) {
                int startIdx = eventString.indexOf("(") + 1;
                int endIdx = eventString.indexOf(")");
                Item originalItem = item;
                Item newItem = GameState.instance().getDungeon().getItem(eventString.substring(startIdx, endIdx));
                return new TransformEvent(originalItem, newItem);

            }
            else if (eventString.indexOf("Eat") != -1) {
                int startIdx = eventString.indexOf("(") + 1;
                int endIdx = eventString.indexOf(")");
                int param = Integer.parseInt(eventString.substring(startIdx, endIdx));
                return new EatEvent(param);
            }
            else if (eventString.indexOf("Damage") != -1) {
                int startIdx = eventString.indexOf("(") + 1;
                int endIdx = eventString.indexOf(")");
                int param = Integer.parseInt(eventString.substring(startIdx, endIdx));
                return new DamageEvent(param);
            }
            else {
                int startIdx = eventString.indexOf("(") + 1;
                int endIdx = eventString.indexOf(")");
                int param = Integer.parseInt(eventString.substring(startIdx, endIdx));
                return new WoundEvent(param);
            }
        }

	}
}

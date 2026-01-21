import java.util.*;

class VerbalTradeNPC extends TradeNPC {

	private ArrayList<String>  quotes = new ArrayList<String>();
    private ArrayList<String> saysSynonyms = new ArrayList<String>(
            Arrays.asList("bequeaths","states","says","proclaims","shouts"));

	VerbalTradeNPC(Scanner s) {
        super(s);

        String[] quotesArr = s.nextLine().split(":")[1].split(",");

        for (String quote : quotesArr) {
            this.quotes.add(quote);
        }
	}

    String speak() {
        Random rand = GameState.instance().getRand();
		int randomNum = rand.nextInt(this.quotes.size());
        int randSaysSyn = rand.nextInt(this.saysSynonyms.size());
		return this.name + " " + this.saysSynonyms.get(randSaysSyn) + " to you, \"" + this.quotes.get(randomNum) + ".\"";
	}
}

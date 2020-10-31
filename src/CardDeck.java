import java.util.ArrayList;
import java.util.List;

public class CardDeck {

  public static List<List<Integer>> Deck;

  public static void initialiseDeck(int playerCount) {
    List<List<Integer>> tempDeck = new ArrayList<>(playerCount);
    for (int i = 0; i < playerCount; i++) {
      tempDeck.add(new ArrayList<Integer>());
    }
    Deck = tempDeck;
  }

  public static void outputDeck() {
    System.out.println(Deck);
  }

  public static synchronized void addCard(int deckNumber, int cardDesignation) {
    System.out.println("Adding: " + cardDesignation + " to Deck: " + deckNumber);
    Deck.get(deckNumber).add(cardDesignation);
  }

  public synchronized int playerMove(int card) {
    int drawnCard = 0;
    return drawnCard;
  }

  public synchronized int drawCard(int deckIndex, int cardDesignation) {
    return 0;
  }
}

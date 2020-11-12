import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CardDeck {

  private final int deckNumber;
  private final ArrayList<Card> deck = new ArrayList<Card>();

  public CardDeck(int deckNumber) throws ObjectReferenceNumberException {;
    if (deckNumber == 0) {
      throw new ObjectReferenceNumberException("Index is 0");
    }
    else if (deckNumber < 0) {
      throw new ObjectReferenceNumberException("Index less than 0");
    }
    else {
      this.deckNumber = deckNumber;
    }
  }

  public ArrayList<Card> getDeck() {
    return this.deck;
  }

  public int getDeckNumber() {
    return deckNumber;
  }

  public synchronized void addCard(Card card) throws FormattingException {
    card.setOwner("d" + this.deckNumber);
    this.deck.add(card);
  }

  public void outputDeckCardDenominationsToFile() {
    try {
      FileWriter myWriter = new FileWriter("deck" + this.deckNumber + "_output.txt", false);
      StringBuilder stringBuilder = new StringBuilder();
      deck.forEach(
          c -> {
            stringBuilder.append(c.getDenomination() + " ");
          });
      String result = stringBuilder.toString().trim();
      myWriter.write("deck" + this.deckNumber + " contents: " + result);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public synchronized Card drawCard() {
    Card returnCard = this.deck.get(0);
    this.deck.remove(0);
    return returnCard;
  }
}

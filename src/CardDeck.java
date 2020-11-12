import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class CardDeck {

  private final int deckNumber;
  private final ArrayList<Card> deck = new ArrayList<Card>();

  public CardDeck(int deckNumber) throws ObjectReferenceNumberException {
    // Ensures deckNumber is 1+
    if (deckNumber <= 0) {
      throw new ObjectReferenceNumberException("deckNumber is 0 or less");
    } else {
      this.deckNumber = deckNumber;
    }
  }

  public ArrayList<Card> getDeck() {
    return this.deck;
  }

  public int getDeckNumber() {
    return deckNumber;
  }

  /**
   * Adds card to the deck object
   *
   * @param card Card to be added to the deck
   * @throws FormattingException if card has invalid current owner format
   */
  public synchronized void addCard(Card card) throws FormattingException {
    // sets card owner to this deck instead of previous owner
    card.setOwner("d" + this.deckNumber);
    // adds card to bottom of the deck
    this.deck.add(card);
  }

  /** outputs decks content to text file with name deck[DECKNUMBER]_output.txt */
  public void outputDeckCardDenominationsToFile() {
    try {
      // creates new file or overwrites file if it already exists
      FileWriter myWriter = new FileWriter("deck" + this.deckNumber + "_output.txt", false);
      StringBuilder stringBuilder = new StringBuilder();
      // adds each card denomination to output string with a space after int
      deck.forEach(
          c -> {
            stringBuilder.append(c.getCardDenomination() + " ");
          });
      // trims white space off the end of the string
      String result = stringBuilder.toString().trim();
      myWriter.write("deck" + this.deckNumber + " contents: " + result);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    } catch (ConcurrentModificationException e) {
      return;
    }
  }

  /**
   * Draws card from top of deck
   *
   * @return Card drawn from top of the deck
   */
  public synchronized Card drawCard() {
    // gets top card of deck
    Card returnCard = this.deck.get(0);
    // removes card from the deck
    this.deck.remove(0);
    return returnCard;
  }
}

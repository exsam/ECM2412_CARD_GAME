import java.util.ArrayList;

public class Player extends Thread{
    private int number;

    private ArrayList<Card> hand;

  /**
   * @param card
   * @param position in ArrayList
   */
    public void addToHand(Card card, int position) {
      //HOW DO WE ACCESS PLAYER Ns DECK
        hand.set(position,card);
    }
    public void addToDeck(CardDeck cardDeck){
      //randomly select card from hand that is not = playerNumber
        //addCard to deck1 BUT
            //WHERE DO WE ADD THE CARD IN DECK1
    }
    public synchronized void playerMove(CardDeck deck){
        addToDeck
    }
}

// {1,1,5,4,3} Deck 1
// {1,2,4,null,4} Player 1
// {1,3,null,5} Deck 2
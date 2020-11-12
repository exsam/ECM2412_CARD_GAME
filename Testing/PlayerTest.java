import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class PlayerTest {

  @Test
  public void testPlayerNumberValid() throws ObjectReferenceNumberException {
    for (int i = 1; i <= 16; i++) {
      // the new player is not used past construction so we suppress this warning
      // to keep the IDE log clear for more relevant errors
      @SuppressWarnings("unused")
      Player testValidPlayer = new Player(i);
      //If no errors thrown then this constructor code works.
    }
  }

  @Test
  public void testPlayerIndexNegative() {
    try {
      //Again, player not used so IDE warning suppressed.
      @SuppressWarnings("unused")
      //Call Player constructor with negative player number.
      Player testNegativeIndexPlayer = new Player(-37);
      //If no error is thrown, fail test
      fail("Fail, No exception thrown");
    } catch (Exception ex) {
      //Test the Exception is not null
      assertNotNull(ex.getMessage());
      //Make sure that the thrown exception message matches the expected exception message.
      assertEquals("Index less than 0", ex.getMessage());
    }
  }

  @Test
  public void testPlayerIndexZero() {
    try {
      //Again, player not used so IDE warning suppressed.
      @SuppressWarnings("unused")
      //Call Player constructor with a zero player number.
      Player testZeroIndexPlayer = new Player(0);
      //If no error is thrown, fail test
      fail("Fail, No exception thrown");
    } catch (Exception ex) {
      //Test the Exception is not null
      assertNotNull(ex.getMessage());
      //Make sure that the thrown exception message matches the expected exception message.
      assertEquals("Index is 0", ex.getMessage());
    }
  }

  @Test
  public void testGetPlayerIndex() throws ObjectReferenceNumberException {
    //Set expected playerIndex
    int playerIndex = 3;
    //Use Player constructor to create new player with this index
    Player testValidPlayer = new Player(playerIndex);
    //assert that the Player.getPlayerNumber() function returns the same
    //playerNumber as the one that was previously set.
    assertEquals(playerIndex, testValidPlayer.getPlayerNumber());
  }

  @Test
  public void testAddCardToHandValid() throws FormattingException, ObjectReferenceNumberException {

    Player testPlayer = new Player(3);
    Card card1 = new Card("P2", 2);
    testPlayer.addCardToHand(card1);
    card1.setOwner("p2");
    assertEquals(card1, testPlayer.getHand().get(0));
  }

  @Test
  public void testRemoveCardFromHandValid()
      throws FormattingException, ObjectReferenceNumberException {
    Player testPlayer = new Player(2);
    Card card1 = new Card("p1", 2);
    testPlayer.addCardToHand(card1);
    testPlayer.removeCard(0);
    assertEquals(0, testPlayer.getHand().size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRemoveCardFromHandOutOfBounds()
      throws FormattingException, ObjectReferenceNumberException {
    Player testPlayer = new Player(2);
    Card card1 = new Card("p1", 2);
    testPlayer.addCardToHand(card1);
    testPlayer.removeCard(-1);
  }

  @Test
  public void testDiscardCardFromHandValid()
      throws FormattingException, ObjectReferenceNumberException {
    // define player with index n
    int playerNumber = 3;
    Player testPlayer = new Player(playerNumber);
    // 3 cards of n, one not of n
    for (int i = 1; i <= 3; i++) {
      Card tempCard = new Card("p2", playerNumber);
      testPlayer.addCardToHand(tempCard);
    }
    Card expectedDiscardedCard = new Card("p2", playerNumber + 1);
    testPlayer.addCardToHand(expectedDiscardedCard);
    expectedDiscardedCard.setOwner("p3");
    Card discardedCard = testPlayer.discardCard();
    assertEquals(discardedCard, expectedDiscardedCard);
  }

  @Test
  public void testGetHand() throws ObjectReferenceNumberException, FormattingException {
    List<Card> expectedPlayerHand = new ArrayList<Card>();
    Player testPlayer = new Player(3);
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", i);
      testPlayer.addCardToHand(tempCard);
      expectedPlayerHand.add(tempCard);
    }
    List<Card> actualPlayerHand = testPlayer.getHand();
    assertEquals(expectedPlayerHand, actualPlayerHand);
  }

  @Test
  public void testGetHandDenominations()
      throws FormattingException, ObjectReferenceNumberException {
    List<Integer> testHandDenominations = new ArrayList<Integer>();
    Player testPlayer = new Player(3);
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", i);
      testPlayer.addCardToHand(tempCard);
      testHandDenominations.add(i);
    }
    List<Integer> actualHandDenominations = testPlayer.getHandDenominations();
    Collections.sort(testHandDenominations);
    Collections.sort(actualHandDenominations);
    assertEquals(testHandDenominations, actualHandDenominations);
  }

  @Test
  public void checkForValidWin() throws ObjectReferenceNumberException, FormattingException {
    // CardGame testGame = new CardGame();
    int playerNumber = 3;
    AtomicBoolean won = new AtomicBoolean(false);
    AtomicInteger winNumber = new AtomicInteger(0);
    Player winningPlayer = new Player(playerNumber);
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", playerNumber);
      winningPlayer.addCardToHand(tempCard);
    }
    winningPlayer.checkForWin(won, winNumber);
    assert (won.get() && winNumber.get() == 3);
  }

  @Test
  public void checkForNotValidWin() throws ObjectReferenceNumberException, FormattingException {
    // CardGame testGame = new CardGame();
    int playerNumber = 3;
    AtomicBoolean won = new AtomicBoolean(false);
    AtomicInteger winNumber = new AtomicInteger(0);
    Player winningPlayer = new Player(playerNumber);
    for (int i = 1; i <= 3; i++) {
      Card tempCard = new Card("p3", playerNumber);
      winningPlayer.addCardToHand(tempCard);
    }
    Card tempCard = new Card("p3", playerNumber + 1);
    winningPlayer.addCardToHand(tempCard);
    winningPlayer.checkForWin(won, winNumber);
    assert (!won.get() && winNumber.get() == 0);
  }
}

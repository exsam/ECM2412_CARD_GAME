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
    //
    for (int i = 1; i <= 16; i++) {
      // the new player is not used past construction so we suppress this warning
      // to keep the IDE log clear for more relevant errors
      @SuppressWarnings("unused")
      Player testValidPlayer = new Player(i);
      // If no errors thrown then this constructor code works.
    }
  }

  @Test
  public void testPlayerIndexNegative() {
    try {
      // Again, player not used so IDE warning suppressed.
      @SuppressWarnings("unused")
      // Call Player constructor with negative player number.
      Player testNegativeIndexPlayer = new Player(-37);
      // If no error is thrown, fail test
      fail("Fail, No exception thrown");
    } catch (Exception ex) {
      // Test the Exception is not null
      assertNotNull(ex.getMessage());
      // Make sure that the thrown exception message matches the expected exception message.
      assertEquals("Index less than 0", ex.getMessage());
    }
  }

  @Test
  public void testPlayerIndexZero() {
    try {
      // Again, player not used so IDE warning suppressed.
      @SuppressWarnings("unused")
      // Call Player constructor with a zero player number.
      Player testZeroIndexPlayer = new Player(0);
      // If no error is thrown, fail test
      fail("Fail, No exception thrown");
    } catch (Exception ex) {
      // Test the Exception is not null
      assertNotNull(ex.getMessage());
      // Make sure that the thrown exception message matches the expected exception message.
      assertEquals("Index is 0", ex.getMessage());
    }
  }

  @Test
  public void testGetPlayerNumber() throws ObjectReferenceNumberException {
    // Set expected playerIndex
    int expectedPlayerNumber = 3;
    // Use Player constructor to create new player with this index
    Player testValidPlayer = new Player(expectedPlayerNumber);
    // assert that the Player.getPlayerNumber() function returns the same
    // playerNumber as the one that was previously set.
    assertEquals(expectedPlayerNumber, testValidPlayer.getPlayerNumber());
  }

  @Test
  public void testAddCardToHandValid() throws FormattingException, ObjectReferenceNumberException {
    // Use Player constructor to create new player with index 3
    Player testPlayer = new Player(3);
    // Create new "Card" object and set owner "Player 2" and "denomination "2".
    Card card1 = new Card("p2", 2);
    // Add card object to the player.
    testPlayer.addCardToHand(card1);
    // Have to manually change ownership as this is automatic
    // in the Card,addCardToHand() function.

    // If we didn't do this the assertion would fail.
    card1.setOwner("p2");
    assertEquals(card1, testPlayer.getHand().get(0));
  }

  @Test
  public void testRemoveCardFromHandValid()
      throws FormattingException, ObjectReferenceNumberException {
    // Use Player constructor to create new player with index 2
    Player testPlayer = new Player(2);
    // Create new "Card" object and set owner "Player 1" and "denomination "2".
    Card card1 = new Card("p1", 2);
    // Add card object to the player.
    testPlayer.addCardToHand(card1);
    // Now remove the bottom card (only card) from the deck.
    testPlayer.removeCard(0);
    // Check that the player's hand size is 0 (ie. has an empty hand).
    assertEquals(0, testPlayer.getHand().size());
  }

  // IF inbuilt Java IndexOutOfBoundsException thrown, we know that the
  // test method catches he below indexing error so the test passes
  @Test(expected = IndexOutOfBoundsException.class)
  public void testRemoveCardFromHandOutOfBounds()
      throws FormattingException, ObjectReferenceNumberException {
    // Same code block as above.
    Player testPlayer = new Player(2);
    Card card1 = new Card("p1", 2);
    testPlayer.addCardToHand(card1);
    // Attempt to remove a card at an a non-existent index (error)
    testPlayer.removeCard(-1);
  }

  @Test
  public void testDiscardCardFromHandValid()
      throws FormattingException, ObjectReferenceNumberException {
    // Use Player constructor to create new player with index 3
    int playerNumber = 3;
    Player testPlayer = new Player(playerNumber);
    // Add 3 cards with denomination matching the playerNumber to that player's hand.
    for (int i = 1; i <= 3; i++) {
      Card tempCard = new Card("p2", playerNumber);
      testPlayer.addCardToHand(tempCard);
    }
    // Add 1 card where the denomination doesn't match the playerNumber
    // this is the expected card to be discarded as it is non preferential.
    Card expectedDiscardedCard = new Card("p2", playerNumber + 1);
    testPlayer.addCardToHand(expectedDiscardedCard);
    // Have to manually changed ownership as this is automatic
    // in the Card,addCardToHand() function.
    expectedDiscardedCard.setOwner("p3");
    Card discardedCard = testPlayer.discardCard();
    // assert that the non preferential card is the card that is
    // discarded by the Player.discardCard() function.
    assertEquals(discardedCard, expectedDiscardedCard);
  }

  @Test
  public void testGetHand() throws ObjectReferenceNumberException, FormattingException {
    // Define Card arrayList to store the expected hand
    List<Card> expectedPlayerHand = new ArrayList<Card>();
    // Use Player constructor to create new player with index 3
    Player testPlayer = new Player(3);
    // Add 4 cards to the players hand with different denominations
    // Also add these cards to the expected hand arrayList
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", i);
      testPlayer.addCardToHand(tempCard);
      expectedPlayerHand.add(tempCard);
    }
    // Store the returned Card arrayList from the Player.getHand() function
    List<Card> actualPlayerHand = testPlayer.getHand();
    // Assert that expectedHand arrayList matches the received hand arrayList.
    assertEquals(expectedPlayerHand, actualPlayerHand);
  }

  @Test
  public void testGetHandDenominations()
      throws FormattingException, ObjectReferenceNumberException {
    // Define integer arrayList to store card denominations
    List<Integer> testHandDenominations = new ArrayList<Integer>();
    // Use Player constructor to create new player with index 3
    Player testPlayer = new Player(3);
    // Add 4 cards to the players hand with different denominations
    // Also add the denominations of these cards to the arrayList
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", i);
      testPlayer.addCardToHand(tempCard);
      testHandDenominations.add(i);
    }
    // Store the returned integer arrayList from the Player.getHandDenominations() function
    List<Integer> actualHandDenominations = testPlayer.getHandDenominations();
    // Sort both of these arrayLists
    Collections.sort(testHandDenominations);
    Collections.sort(actualHandDenominations);
    // Assert that expected arrayList of denominations matches the received one.
    assertEquals(testHandDenominations, actualHandDenominations);
  }

  @Test
  public void checkForValidWin() throws ObjectReferenceNumberException, FormattingException {
    // Use Player constructor to create new player with index 3
    int playerNumber = 3;
    Player winningPlayer = new Player(playerNumber);
    // Define AtomicBool and AtomicInteger values
    AtomicBoolean won = new AtomicBoolean(false);
    AtomicInteger winNumber = new AtomicInteger(0);
    // Add 4 cards that have matching denomination to the playerNumber
    // to that player's hand giving them a valid winning hand.
    for (int i = 1; i <= 4; i++) {
      Card tempCard = new Card("p3", playerNumber);
      winningPlayer.addCardToHand(tempCard);
    }
    // Check that this player has won using the Player.checkForWin() function.
    winningPlayer.checkForWin(won, winNumber);
    // Assert that the AtomicBool won is true and the winning player
    // is the same one we constructed with playerNumber = 3.
    assert (won.get() && winNumber.get() == 3);
  }

  @Test
  public void checkForNotValidWin() throws ObjectReferenceNumberException, FormattingException {
    // Same code block as above.
    int playerNumber = 3;
    Player winningPlayer = new Player(playerNumber);
    AtomicBoolean won = new AtomicBoolean(false);
    AtomicInteger winNumber = new AtomicInteger(0);
    // Add 3 cards that have matching denomination to the playerNumber to that player's hand
    for (int i = 1; i <= 3; i++) {
      Card tempCard = new Card("p3", playerNumber);
      winningPlayer.addCardToHand(tempCard);
    }
    // Add a card to the player's hand that does not have matching denomination
    // This does not create a valid winning hand so we must test this
    Card tempCard = new Card("p3", playerNumber + 1);
    winningPlayer.addCardToHand(tempCard);
    winningPlayer.checkForWin(won, winNumber);
    // Check that AtomicBoolean flag "won" remains false and
    // that the winNumber remains it's assigned default value
    assert (!won.get() && winNumber.get() == 0);
  }
}

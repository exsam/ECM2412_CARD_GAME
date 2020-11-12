import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

public class CardGameTest {

  @Test
  public void importPackFile() {
    CardGame testGame = new CardGame();
    Scanner testFileInput = new Scanner("Testing/testImportPack.txt");
    ArrayList<Integer> loadedIntegerPack = CardGame.importPackFile(testFileInput, 2);
    AtomicBoolean notEqual = new AtomicBoolean(false);
    if(loadedIntegerPack.size() != 16) {notEqual.set(true);}
    loadedIntegerPack.forEach(
        i -> {
          if (i != 3) {
            notEqual.set(true);
          }
        });
    assertEquals(notEqual.get(), false);
  }

  @Test
  public void testPopulateGameFunc() throws ObjectReferenceNumberException {
    ArrayList<Integer> inputPack =
        new ArrayList<Integer>(Arrays.asList(1, 2, 1, 2, 1, 2, 1, 2, 3, 4, 3, 4, 3, 4, 3, 4));
    Player[] playerList = new Player[2];
    playerList[0] = new Player(1);
    System.out.println(playerList[0]);
    playerList[1] = new Player(2);
    CardDeck[] deckList = new CardDeck[2];
    deckList[0] = new CardDeck(1);
    deckList[1] = new CardDeck(2);
    CardGame.populateGame(playerList, deckList, inputPack);
    ArrayList<Integer> testPlayer = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1));
    ArrayList<Integer> testDecks = new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3));
    assert (playerList[0].getHandDenominations().equals(testPlayer));
  }

  @Ignore("Due to game not being populated, causes IndexOutOfBoundsError but test yet to fail")
  @Test
  public void testPlayerThreadSpawn() throws ObjectReferenceNumberException {
    Player[] playerList = new Player[2];
    playerList[0] = new Player(1);
    playerList[1] = new Player(2);
    CardGame.startPlayerThreads(playerList);
    assert (playerList[0].isAlive() && playerList[1].isAlive());
  }
}

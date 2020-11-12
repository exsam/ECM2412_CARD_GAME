import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CardGameTest {

    //CODE TO CHECK SAME NUMBER OF DECKS AS PLAYERS

    //NEED SAM TO MAKE GAME A FUNCTION THAT TAKES PARAMETERS
    //SEPERATE FUNCTION OF INPUTS THAT IS PASSED TO GAME FUNCTION
    //OTHERWISE CANNOT TEST FUNCTION AS INPUTS ARE BLOCKING

    @Test
    public void getPlayerCount() {
    }

    @Test
    public void importPackFile() {

    }

    @Test
    public void testPopulateGameFunc() throws ObjectReferenceNumberException {
        ArrayList<Integer> inputPack = new ArrayList<Integer>(Arrays.asList(1, 2, 1, 2, 1, 2, 1, 2, 3, 4, 3, 4, 3, 4, 3, 4));
        Player[] playerList = new Player[2];
        playerList[0] = new Player(1);
        System.out.println(playerList[0]);
        playerList[1] = new Player(2);
        CardDeck[] deckList = new CardDeck[2];
        deckList[0] = new CardDeck(1);
        deckList[1] = new CardDeck(2);
        CardGame testGame = new CardGame();
        testGame.populateGame(playerList,deckList,inputPack);
        ArrayList<Integer> testPlayer = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1));
        assertEquals(playerList[0].getHandDenominations(), testPlayer);
    }


    @Test
    public void testPlayerThreadSpawn() throws ObjectReferenceNumberException {
        Player[] playerList = new Player[2];
        playerList[0] = new Player(1);
        playerList[1] = new Player(2);
        CardGame testinGame = new CardGame();
        testinGame.startPlayerThreads(playerList);
        assert(playerList[0].isAlive() && playerList[1].isAlive());
    }

    //@Test
    //public void main() {
    //}
}
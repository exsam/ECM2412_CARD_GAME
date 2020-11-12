import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardDeckTest {

    @Test
    public void testGetDeckNumber() throws ObjectReferenceNumberException {
        int expectedDeckNumber = 3;
        CardDeck testDeck = new CardDeck(expectedDeckNumber);
        int actualDeckNumber = testDeck.getDeckNumber();
        assertEquals(expectedDeckNumber, actualDeckNumber);
    }

    @Test
    public void testDeckToFileOutput() throws ObjectReferenceNumberException, FormattingException {
        int testDeckNumber = 100;
        CardDeck testDeck = new CardDeck(testDeckNumber);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            Card tempCard = new Card("p1", i);
            testDeck.addCard(tempCard);
            stringBuilder.append(tempCard.getDenomination() + " ");
        }
        String expectedHandString = stringBuilder.toString().trim();
        String expectedFileContents = "deck" + testDeckNumber + " contents: " + expectedHandString;
        String actualFileContents = null;
        testDeck.outputDeckCardDenominationsToFile();
        String pathname = "deck" + testDeckNumber + "_output.txt";
        try {
            actualFileContents = new Scanner(new File(pathname)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void testDeckIndexValid() throws ObjectReferenceNumberException {
        for (int i = 1; i <= 64; i++) {
            @SuppressWarnings("unused")
            CardDeck testValidDeck = new CardDeck(i);
        }
    }

    @Test
    public void testDeckNumberNegative() throws ObjectReferenceNumberException {
        try {
            @SuppressWarnings("unused")
            CardDeck testNegativeIndexDeck = new CardDeck(-7);
            //This originally tested that the index was negative but it actually cannot be below 2 otherwise game doesn't work!
            fail("Failed to assert :No exception thrown");
        } catch (Exception ex) {
            assertNotNull("Failed to assert", ex.getMessage());
            assertEquals("Failed to assert", "Index less than 0", ex.getMessage());
        }
    }

    @Test
    public void testDeckNumberZero() throws ObjectReferenceNumberException {
        try {
            @SuppressWarnings("unused")
            CardDeck testZeroIndexDeck = new CardDeck(0);
            fail("Failed to assert :No exception thrown");
        } catch (Exception ex) {
            assertNotNull("Failed to assert", ex.getMessage());
            assertEquals("Failed to assert", "Index is 0", ex.getMessage());
        }
    }

    @Test
    public void testGetDeck() throws FormattingException, ObjectReferenceNumberException {
        List<Card> expectedDeck = new ArrayList<Card>();
        CardDeck testDeck = new CardDeck(3);
        for (int i = 1; i <= 4; i++) {
            Card tempCard = new Card ("p2", i);
            testDeck.addCard(tempCard);
            //Change card ownership after adding to deck
            //CardDeck class should automatically reassign ownership
            tempCard.setOwner("d3");
            //Add to expectedDeck ArrayList
            expectedDeck.add(tempCard);
        }
        List<Card> actualDeck = testDeck.getDeck();
        //These should be the same so assertEquals
        assertEquals(expectedDeck, actualDeck);
    }

    @Test
    public void testAddToDeck() throws FormattingException, ObjectReferenceNumberException {
        Card testCard = new Card("p3", 2);
        CardDeck testDeck = new CardDeck(4);
        testDeck.addCard(testCard);
        //Owner of CardDeck should change to "D4" within CardDeck class
        //So for testing we manually change our testCard's ownership
        testCard.setOwner("d4");
        Card actualCard = testDeck.getDeck().get(0);
        //Now assert the two cards are the same
        assertEquals(testCard, actualCard);
    }

    @Test
    public void testDrawCard() throws ObjectReferenceNumberException, FormattingException {
        CardDeck testDeck = new CardDeck(1);
        Card expectedDrawn = null;
        for (int i = 1; i <= 4; i++) {
            Card testCard = new Card("p4", i);
            testDeck.addCard(testCard);
            if (i == 1) {
                expectedDrawn = testCard;
            }
        }
        Card actualDrawn = testDeck.drawCard();
        assertEquals(expectedDrawn, actualDrawn);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDrawCardEmptyDeck() throws ObjectReferenceNumberException, FormattingException {
        CardDeck testDeck = new CardDeck(1);
        System.out.println(testDeck.getDeck().get(0));
    }
}
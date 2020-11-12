import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CardTest {

    //Variables initialised for testing purposes
    public Card testCard;
    public String Owner;
    public int Denomination;

    @Before
    public void cardInit() {
        //Card Constructor to run before all test cases
        Owner = "p1";
        Denomination = 2;
        //Create new "Card" object and set "Owner" and "Denomination"
        testCard = new Card(Owner, Denomination);
    }

    @Test
    public void testGetOwner() {
        //Test that the global owner is the same
        //as the owner from the Card.getOwner() method
        assertEquals(Owner, testCard.getOwner());
    }

    @Test
    public void testSetOwnerValid() throws FormattingException {
        //Set new owner to "Player 2"
        String newOwner = "p2";
        //call Card.SetOwner() to change ownership string
        testCard.setOwner(newOwner);
        //As Card.GetOwner(0 works, we can use this
        //in the assertion to check the new owner value
        //of the card matches the owner value we set it to
        assertEquals(newOwner, testCard.getOwner());
    }

    //IF FormattingException thrown, we know test method catches the
    // below formatting error so the test passes
    @Test(expected = FormattingException.class)
    public void testSetOwnerInvalid() throws FormattingException {
        //Set new owner to "2" (invalid format)
        String newOwner = "2";
        //Call Card,setOwner() with this invalid input format (error)
        testCard.setOwner(newOwner);
    }

    @Test
    public void testGetDenomination() {
        //Check Card.getDenomination value matches the global denomination set.
        assertEquals(Denomination, testCard.getCardDenomination());
    }
}
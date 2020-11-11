import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CardTest {

    public Card testCard;
    public String Owner;
    public int Denomination;

    @Before
    public void cardInit() {
        Owner = "p1";
        Denomination = 2;
        testCard = new Card(Owner, Denomination);
    }

    @Test
    public void testGetOwner() {
        assertEquals(Owner, testCard.getOwner());
    }

    @Test
    public void testSetOwnerValid() throws FormattingException {
        String newOwner = "p2";
        testCard.setOwner(newOwner);
        assertEquals(newOwner, testCard.getOwner());
    }

    @Test(expected = FormattingException.class)
    public void testSetOwnerInvalid() throws FormattingException {
        String newOwner = "2";
        // THis will value because there is no "P" or "D" prefix for newOwner.
        testCard.setOwner(newOwner);
    }

    @Test
    public void testGetDenomination() {
        assertEquals(Denomination, testCard.getDenomination());
    }
}
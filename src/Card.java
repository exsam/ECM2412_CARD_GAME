public class Card {

  private final int denomination;
  private String owner;

  public Card(String owner, int denomination) {
    this.owner = owner;
    this.denomination = denomination;
  }

  public String getOwner() {
    return owner;
  }

  /**
   * Sets owner of card.
   *
   * @param owner pX or dX dependent on if deck or player holding card
   * @throws FormattingException
   */
  public void setOwner(String owner) throws FormattingException {
    // Ensures that owner is correct format
    char firstChar = owner.charAt(0);
    if (firstChar == 'p' || firstChar == 'd') {
      this.owner = owner;
    } else {
      throw new FormattingException("Owner should start with p or d.");
    }
  }

  public int getCardDenomination() {
    return denomination;
  }
}

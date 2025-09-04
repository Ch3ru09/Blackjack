package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerStatus {
  private ArrayList<String> cards = new ArrayList<>();
  private int playerTotal = 0;
  private int availableAces = 0;

  public ArrayList<String> getCards() {
    return this.cards;
  }

  public int getTotal() {
    return this.playerTotal;
  }

  private void addToTotal(int delta) {
    this.playerTotal += delta;
  }

  private void addToCards(String card) {
    this.cards.add(card);
  }

  public void addNewCard(Object[] card) {
    addToCards((String) card[0]);
    addToTotal((int) card[1]);
    if (card[0].equals("Ace")) {
      this.availableAces++;
    }
  }

  public boolean bust() {
    if (this.playerTotal < 22) {
      return false;
    }
    if (availableAces > 0) {
      this.playerTotal -= 10;
      this.availableAces--;
      return false;
    }
    return true;
  }

  public void clear() {
    this.cards.clear();
    this.playerTotal = 0;
  }

  public int nbAce() {
    return Collections.frequency(cards, "Ace");
  }
}

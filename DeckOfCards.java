package blackjack;

import java.util.ArrayList;
import java.util.Random;

class DeckOfCards {
  final private static String[] RANKS = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen",
      "King" };
  final private static int SUITS = 4;

  private ArrayList<String> deck = new ArrayList<>();
  private static Random rand = new Random();

  public static Object[] getObject(String name, int value) {
    return new Object[] { name, value };
  }

  public DeckOfCards(int nbDecks) {
    populate(nbDecks);

    shuffle();
  }

  void populate(int nbDecks) {
    for (int i = 0; i < RANKS.length; i++) {
      for (int j = 0; j < SUITS; j++) {
        for (int n = 0; n < nbDecks; n++) {
          this.deck.add(RANKS[i]);
        }
      }
    }
  }

  void shuffle() {
    for (int i = 0; i < this.deck.size(); i++) {
      int r = i + (int) (Math.random() * (SUITS * RANKS.length - i));
      String temp = this.deck.get(r);
      this.deck.set(r, this.deck.get(i));
      this.deck.set(i, temp);
    }
  }

  // void showDeck() {
  // System.out.println(this.deck.toString()));
  // }

  public void resetDeck(int nbDecks) {
    this.deck.clear();

    populate(nbDecks);
    shuffle();
  }

  public int size() {
    return this.deck.size();
  }

  Object[] dealNewCard() {
    int randint = rand.nextInt(this.deck.size());
    String res = this.deck.get(randint);
    this.deck.remove(randint);

    int value = 0;

    if (res.equals("King") || res.equals("Queen") || res.equals("Jack")) {
      value = 10;
    } else if (res.equals("Ace")) {
      value = 11;
    } else {
      value = Integer.parseInt(res);
    }

    return getObject(res, value);
  }
}
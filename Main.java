package blackjack;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
  private static PlayerStatus player = new PlayerStatus();
  private static PlayerStatus house = new PlayerStatus();
  private static BankAccount bankAccount = new BankAccount();

  private final static int nbDecks = 2;

  private static int bet = 0;

  public static void main(String[] args) {
    DeckOfCards deck = new DeckOfCards(nbDecks);
    try (Scanner input = new Scanner(System.in)) {
      while (true) {
        deckloop: while (deck.size() > 10) {
          clear();
          System.out.println("Current balance: " + bankAccount.balance());
          System.out.print("What's your bet? ");
          bet = Math.min(input.nextInt(), bankAccount.balance());

          bankAccount.changeBalance(-bet);
          input.nextLine();
          clear();

          player.clear();
          house.clear();

          player.addNewCard(deck.dealNewCard());
          player.addNewCard(deck.dealNewCard());
          house.addNewCard(deck.dealNewCard());
          house.addNewCard(deck.dealNewCard());

          loop: while (true) {
            System.out.println("Current balance: " + bankAccount.balance());
            System.out.println("Your bet: " + bet);
            System.out.println("House: [" + house.getCards().toArray()[0] + ", \u2588\u2588\u2588\u2588\u2588 ]");
            System.out.println("You: " + Arrays.toString(player.getCards().toArray()));

            System.out.print("hit(h) or stand(s): ");
            String choice = input.nextLine();

            // clear
            clear();

            if (choice.equals("hit") || choice.equals("h")) {
              player.addNewCard(deck.dealNewCard());
              if (player.bust()) {
                break loop;
              }
              continue loop;
            }

            if (choice.equals("stand") || choice.equals("s")) {
              break loop;
            }

            // else
            System.out.println("What is so hard about choosing one of the options???");

          }

          if (player.getTotal() > 21) {
            System.out.println("You: " + player.getCards().toString() + " bust");
            System.out.println("\nYou suck\n");
            timeout(3);
          } else {
            System.out.println("Your bet: " + bet);
            System.out.println("House: " + house.getCards().toString());
            System.out.println("You: " + player.getCards().toString());
            while (house.getTotal() < 17) {
              timeout(3);
              house.addNewCard(deck.dealNewCard());
              clear();
              System.out.println("Your bet: " + bet);
              System.out.println(
                  "House: " + house.getCards().toString() + (house.bust() ? " bust" : "") + " " + house.getTotal());
              System.out.println(
                  "You: " + player.getCards().toString() + (player.bust() ? " bust" : "") + " " + player.getTotal());
            }

            if (house.bust()) {
              System.out.println("\nYou win\n");
              bankAccount.changeBalance(bet * 2);
            } else if (player.getTotal() > house.getTotal()) {
              System.out.println("\nYou win\n");
              bankAccount.changeBalance(bet * 2);
            } else if (player.getTotal() < house.getTotal()) {
              System.out.println("\nYou lost\n");
            } else if (player.getTotal() == house.getTotal()) {
              System.out.println("\nTie?\n");
              bankAccount.changeBalance(bet);
            }
            timeout(3);
          }

          System.out.println("Current balance: " + bankAccount.balance());
          System.out.print("Continue? yes(y)/no(n): ");
          String continuing = input.next();

          if (continuing.equals("yes") || continuing.equals("y")) {
            if (bankAccount.balance() < 100) {
              System.out.print("Would you like to take a loan? Yes(y)/No(n): ");
              String res = input.next();
              if (res.equals("yes") || res.equals("y")) {
                bankAccount.getLoan();
              } else if (res.equals("no") || res.equals("n")) {
                System.out.println("You are broke, quitting...");
                timeout(3);
                return;
              } else {
                timeout(3);
              }
            }
            continue deckloop;
          } else if (continuing.equals("no") || continuing.equals("n")) {
            return;
          } else {
            System.out.println("Welp, since you decided to not choose either choices, game will continue...");
            timeout(3);
          }
        }

        deck.resetDeck(nbDecks);
      }
    }
  }

  private static void clear() {
    System.out.println("\033[2J\033[H");
  }

  private static void timeout(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

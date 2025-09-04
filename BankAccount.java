package blackjack;

public class BankAccount {
  private int balance = 100_000;

  public int balance() {
    return this.balance;
  }

  public void getLoan() {
    this.balance += 10_000;
  }

  public void changeBalance(int delta) {
    this.balance += delta;
  }
}

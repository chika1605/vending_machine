package model;

public class CoinAcceptor implements PaymentAcceptor {
    private int balance;

    public CoinAcceptor(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addFunds(int amount) {
        balance += amount;
    }

    @Override
    public void deductFunds(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Недостаточно средств.");
        }
    }

    @Override
    public boolean canAfford(int price) {
        return balance >= price;
    }
}

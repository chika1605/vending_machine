package model;

public interface PaymentAcceptor {
    int getBalance();
    void addFunds(int amount);
    void deductFunds(int amount);
    boolean canAfford(int price);
}

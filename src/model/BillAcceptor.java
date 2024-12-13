package model;

import java.util.Scanner;

public class BillAcceptor implements PaymentAcceptor {
    private int balance;

    public BillAcceptor(int i) {
        this.balance = 0;
    }

    @Override
    public int getBalance() {
        return balance;
    }


    @Override
    public void addFunds(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номинал купюры: ");
        int enteredAmount = scanner.nextInt();
        if (isValidBill(enteredAmount)) {
            balance += enteredAmount;
            System.out.println("Купюра номиналом " + enteredAmount + " успешно добавлена.");
        } else {
            System.out.println("Неверный номинал купюры: " + enteredAmount);
        }
    }


    @Override
    public void deductFunds(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Списано " + amount + " с баланса.");
        } else {
            System.out.println("Недостаточно средств для списания.");
        }
    }

    @Override
    public boolean canAfford(int price) {
        return balance >= price;
    }

    private boolean isValidBill(int amount) {
        int[] validBills = {10, 20, 50, 100, 200, 500};
        for (int bill : validBills) {
            if (amount == bill) {
                return true;
            }
        }
        return false;
    }
}

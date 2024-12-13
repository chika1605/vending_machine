package model;

import java.util.Scanner;

public class CardAcceptor implements PaymentAcceptor {
    private int balance;

    public CardAcceptor(int i) {
        this.balance = 0;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addFunds(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты:");
        String cardNumber = scanner.nextLine();
        System.out.println("Введите одноразовый пароль:");
        String otp = scanner.nextLine();

        if (validateCard(cardNumber, otp)) {
            balance += amount;
            System.out.println("Средства успешно добавлены через карту.");
        } else {
            System.out.println("Ошибка при оплате картой.");
        }
    }

    @Override
    public void deductFunds(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Списано " + amount + " с баланса карты.");
        } else {
            System.out.println("Недостаточно средств на карте.");
        }
    }

    @Override
    public boolean canAfford(int price) {
        return balance >= price;
    }

    private boolean validateCard(String cardNumber, String otp) {
        return cardNumber.matches("\\d{16}") && otp.matches("\\d{4}");
    }
}

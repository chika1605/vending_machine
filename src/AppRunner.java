import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final PaymentAcceptor paymentAcceptor;

    private static boolean isExit = false;

    private AppRunner(PaymentAcceptor paymentAcceptor) {
        this.paymentAcceptor = paymentAcceptor;
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run(PaymentAcceptor paymentAcceptor) {
        AppRunner app = new AppRunner(paymentAcceptor);
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        printPaymentMethod();
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + paymentAcceptor.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }


    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentAcceptor.canAfford(products.get(i).getPrice())) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            paymentAcceptor.addFunds(10);
            print("Вы пополнили баланс на 10");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    paymentAcceptor.deductFunds(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                chooseAction(products);
            }
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void printPaymentMethod() {
        if (paymentAcceptor instanceof CoinAcceptor) {
            print("Вы используете монетоприемник.");
        } else if (paymentAcceptor instanceof BillAcceptor) {
            print("Вы используете купюроприемник.");
        } else if (paymentAcceptor instanceof CardAcceptor) {
            print("Вы используете оплату картой.");
        }
    }




    private void print(String msg) {
        System.out.println(msg);
    }
}

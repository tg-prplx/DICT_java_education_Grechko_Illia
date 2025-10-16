package CoffeeMachine;

import java.util.Scanner;

public class CoffeeMachine {
    private enum State {
        CHOOSING_ACTION,
        CHOOSING_COFFEE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_BEANS,
        FILLING_CUPS,
        OFF
    }

    private State state = State.CHOOSING_ACTION;

    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private final Recipe ESPRESSO = new Recipe(250, 0, 16, 4);
    private final Recipe LATTE = new Recipe(350, 75, 20, 7);
    private final Recipe CAPPUCCINO = new Recipe(200, 100, 12, 6);

    private static class Recipe {
        int water;
        int milk;
        int beans;
        int price;
        Recipe(int w, int m, int b, int p) {
            water = w;
            milk = m;
            beans = b;
            price = p;
        }
    }

    public void process(String input) {
        switch (state) {
            case CHOOSING_ACTION:
                handleAction(input.trim());
                break;
            case CHOOSING_COFFEE:
                handleBuy(input.trim());
                break;
            case FILLING_WATER:
                addWater(input.trim());
                break;
            case FILLING_MILK:
                addMilk(input.trim());
                break;
            case FILLING_BEANS:
                addBeans(input.trim());
                break;
            case FILLING_CUPS:
                addCups(input.trim());
                break;
            case OFF:
                break;
        }
    }

    private void handleAction(String cmd) {
        if (cmd.equals("buy")) {
            state = State.CHOOSING_COFFEE;
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to main menu:");
        } else if (cmd.equals("fill")) {
            state = State.FILLING_WATER;
            System.out.println("Write how many ml of water do you want to add:");
        } else if (cmd.equals("take")) {
            System.out.println("I gave you " + money);
            money = 0;
            promptAction();
        } else if (cmd.equals("remaining")) {
            printState();
            promptAction();
        } else if (cmd.equals("exit")) {
            state = State.OFF;
        } else {
            promptAction();
        }
    }

    private void handleBuy(String choice) {
        if (choice.equals("back")) {
            state = State.CHOOSING_ACTION;
            promptAction();
            return;
        }
        Recipe r = null;
        if (choice.equals("1")) r = ESPRESSO;
        else if (choice.equals("2")) r = LATTE;
        else if (choice.equals("3")) r = CAPPUCCINO;
        else {
            // неверный ввод, вернёмся к выбору
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to main menu:");
            return;
        }

        String lack = lackResource(r);
        if (lack != null) {
            System.out.println("Sorry, not enough " + lack + "!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= r.water;
            milk -= r.milk;
            beans -= r.beans;
            cups -= 1;
            money += r.price;
        }
        state = State.CHOOSING_ACTION;
        promptAction();
    }

    private String lackResource(Recipe r) {
        if (water < r.water) return "water";
        if (milk < r.milk) return "milk";
        if (beans < r.beans) return "coffee beans";
        if (cups < 1) return "disposable cups";
        return null;
    }

    private void addWater(String s) {
        int v = parseIntSafe(s);
        water += v;
        state = State.FILLING_MILK;
        System.out.println("Write how many ml of milk do you want to add:");
    }

    private void addMilk(String s) {
        int v = parseIntSafe(s);
        milk += v;
        state = State.FILLING_BEANS;
        System.out.println("Write how many grams of coffee beans do you want to add:");
    }

    private void addBeans(String s) {
        int v = parseIntSafe(s);
        beans += v;
        state = State.FILLING_CUPS;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
    }

    private void addCups(String s) {
        int v = parseIntSafe(s);
        cups += v;
        state = State.CHOOSING_ACTION;
        promptAction();
    }

    private void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
        System.out.println();
    }

    private void promptAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        Scanner sc = new Scanner(System.in);
        machine.promptAction();
        while (machine.state != State.OFF && sc.hasNextLine()) {
            String line = sc.nextLine();
            machine.process(line);
        }
    }
}

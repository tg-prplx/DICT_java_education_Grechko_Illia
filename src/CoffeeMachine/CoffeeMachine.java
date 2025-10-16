package CoffeeMachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * State-driven coffee machine simulator that supports buying drinks, refilling resources,
 * taking money, and displaying the remaining supplies.
 */
public class CoffeeMachine {
    private enum MachineState {
        CHOOSING_ACTION,
        BUYING,
        FILL_WATER,
        FILL_MILK,
        FILL_BEANS,
        FILL_CUPS,
        EXIT
    }

    private static final class Recipe {
        final int water;
        final int milk;
        final int beans;
        final int cost;

        Recipe(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }

    private final Map<String, Recipe> recipes = new HashMap<>();

    private MachineState state = MachineState.CHOOSING_ACTION;
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    public CoffeeMachine() {
        recipes.put("1", new Recipe(250, 0, 16, 4));   // espresso
        recipes.put("2", new Recipe(350, 75, 20, 7));  // latte
        recipes.put("3", new Recipe(200, 100, 12, 6)); // cappuccino
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        System.out.println(machine.getPrompt());
        while (machine.isRunning() && scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String response = machine.process(input);
            if (!response.isEmpty()) {
                System.out.println(response);
            }
            if (machine.isRunning()) {
                System.out.println(machine.getPrompt());
            }
        }
    }

    public String process(String input) {
        String trimmedInput = input == null ? "" : input.trim();
        switch (state) {
            case CHOOSING_ACTION:
                return handleAction(trimmedInput);
            case BUYING:
                return handleBuy(trimmedInput);
            case FILL_WATER:
                return handleFillWater(trimmedInput);
            case FILL_MILK:
                return handleFillMilk(trimmedInput);
            case FILL_BEANS:
                return handleFillBeans(trimmedInput);
            case FILL_CUPS:
                return handleFillCups(trimmedInput);
            case EXIT:
            default:
                return "";
        }
    }

    public boolean isRunning() {
        return state != MachineState.EXIT;
    }

    public String getPrompt() {
        switch (state) {
            case CHOOSING_ACTION:
                return "Write action (buy, fill, take, remaining, exit):";
            case BUYING:
                return "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case FILL_WATER:
                return "Write how many ml of water you want to add:";
            case FILL_MILK:
                return "Write how many ml of milk you want to add:";
            case FILL_BEANS:
                return "Write how many grams of coffee beans you want to add:";
            case FILL_CUPS:
                return "Write how many disposable cups you want to add:";
            case EXIT:
            default:
                return "";
        }
    }

    private String handleAction(String action) {
        switch (action) {
            case "buy":
                state = MachineState.BUYING;
                return "";
            case "fill":
                state = MachineState.FILL_WATER;
                return "";
            case "take":
                int payout = money;
                money = 0;
                return "I gave you " + payout + " грн";
            case "remaining":
                return renderState();
            case "exit":
                state = MachineState.EXIT;
                return "";
            default:
                return "Unknown action";
        }
    }

    private String handleBuy(String selection) {
        if ("back".equals(selection)) {
            state = MachineState.CHOOSING_ACTION;
            return "";
        }

        Recipe recipe = recipes.get(selection);
        if (recipe == null) {
            return "Unknown drink selection";
        }

        String shortage = findShortage(recipe);
        state = MachineState.CHOOSING_ACTION;
        if (shortage != null) {
            return "Sorry, not enough " + shortage + "!";
        }

        water -= recipe.water;
        milk -= recipe.milk;
        beans -= recipe.beans;
        cups -= 1;
        money += recipe.cost;
        return "I have enough resources, making you a coffee!";
    }

    private String handleFillWater(String amount) {
        water += parsePositiveInt(amount);
        state = MachineState.FILL_MILK;
        return "";
    }

    private String handleFillMilk(String amount) {
        milk += parsePositiveInt(amount);
        state = MachineState.FILL_BEANS;
        return "";
    }

    private String handleFillBeans(String amount) {
        beans += parsePositiveInt(amount);
        state = MachineState.FILL_CUPS;
        return "";
    }

    private String handleFillCups(String amount) {
        cups += parsePositiveInt(amount);
        state = MachineState.CHOOSING_ACTION;
        return "";
    }

    private int parsePositiveInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Expected an integer number", ex);
        }
    }

    private String findShortage(Recipe recipe) {
        if (water < recipe.water) {
            return "water";
        }
        if (milk < recipe.milk) {
            return "milk";
        }
        if (beans < recipe.beans) {
            return "coffee beans";
        }
        if (cups < 1) {
            return "disposable cups";
        }
        return null;
    }

    private String renderState() {
        StringBuilder sb = new StringBuilder();
        sb.append("The coffee machine has:\n");
        sb.append(water).append(" ml of water\n");
        sb.append(milk).append(" ml of milk\n");
        sb.append(beans).append(" g of coffee beans\n");
        sb.append(cups).append(" disposable cups\n");
        sb.append(money).append(" грн of money");
        return sb.toString();
    }
}

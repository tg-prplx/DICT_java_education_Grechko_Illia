package RockPaperScissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);

        int rating = loadRating(name);

        String optionsLine = scanner.nextLine().trim();
        String[] options;
        if (optionsLine.isEmpty()) {
            options = new String[]{"rock", "paper", "scissors"};
        } else {
            String[] raw = optionsLine.split(",");
            List<String> list = new ArrayList<>();
            for (String s : raw) {
                String t = s.trim();
                if (!t.isEmpty()) {
                    list.add(t);
                }
            }
            options = list.toArray(new String[0]);
        }

        System.out.println("Okay, let's start");

        Random random = new Random();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("!exit")) {
                System.out.println("Bye!");
                break;
            }
            if (input.equals("!rating")) {
                System.out.println("Your rating: " + rating);
                continue;
            }

            if (!containsOption(options, input)) {
                System.out.println("Invalid input");
                continue;
            }

            String computerChoice = options[random.nextInt(options.length)];

            if (computerChoice.equals(input)) {
                System.out.println("There is a draw (" + computerChoice + ")");
                rating += 50;
            } else {
                String result = getResult(options, input, computerChoice);
                if (result.equals("win")) {
                    System.out.println("Well done. The computer chose " + computerChoice + " and failed");
                    rating += 100;
                } else {
                    System.out.println("Sorry, but the computer chose " + computerChoice);
                }
            }
        }
    }

    private static int loadRating(String name) {
        int rating = 0;
        File file = new File("rating.txt");
        if (!file.exists()) {
            return rating;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String userName = fileScanner.next();
                if (!fileScanner.hasNextInt()) {
                    if (fileScanner.hasNext()) {
                        fileScanner.next();
                    }
                    continue;
                }
                int score = fileScanner.nextInt();
                if (userName.equals(name)) {
                    rating = score;
                }
            }
        } catch (FileNotFoundException e) {
            return rating;
        }
        return rating;
    }

    private static boolean containsOption(String[] options, String value) {
        for (String option : options) {
            if (option.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static String getResult(String[] options, String userChoice, String computerChoice) {
        int n = options.length;
        int userIndex = -1;
        for (int i = 0; i < n; i++) {
            if (options[i].equals(userChoice)) {
                userIndex = i;
                break;
            }
        }
        if (userIndex == -1) {
            return "lose";
        }

        List<String> others = new ArrayList<>();
        for (int i = userIndex + 1; i < n; i++) {
            others.add(options[i]);
        }
        for (int i = 0; i < userIndex; i++) {
            others.add(options[i]);
        }

        int half = others.size() / 2;
        int indexComputer = others.indexOf(computerChoice);
        if (indexComputer == -1) {
            return "lose";
        }
        if (indexComputer < half) {
            return "lose";
        } else {
            return "win";
        }
    }
}

package Hangman;

import java.util.*;

public class hangman {
    public static void main(String[] args) {
        System.out.println("HANGMAN");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Type \"play\" to play the game, \"exit\" to quit: ");
            String cmd = sc.nextLine().trim();
            if ("play".equals(cmd)) {
                play(sc);
            } else if ("exit".equals(cmd)) {
                break;
            }
        }
    }

    static void play(Scanner sc) {
        List<String> words = Arrays.asList("python", "java", "javascript", "kotlin");
        String answer = words.get(new Random().nextInt(words.size()));
        char[] mask = new char[answer.length()];
        Arrays.fill(mask, '-');
        int lives = 8;
        Set<Character> guessed = new HashSet<>();
        while (lives > 0 && !(new String(mask)).equals(answer)) {
            System.out.println();
            System.out.println(new String(mask));
            System.out.print("Input a letter: ");
            String s = sc.nextLine();
            if (s.length() != 1) {
                System.out.println("You should input a single letter");
                continue;
            }
            char ch = s.charAt(0);
            if (ch < 'a' || ch > 'z') {
                System.out.println("Please enter a lowercase English letter");
                continue;
            }
            if (guessed.contains(ch)) {
                System.out.println("You've already guessed this letter");
                continue;
            }
            guessed.add(ch);
            boolean improved = false;
            boolean inWord = false;
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == ch) {
                    inWord = true;
                    if (mask[i] != ch) { mask[i] = ch; improved = true; }
                }
            }
            if (!inWord) {
                System.out.println("That letter doesn't appear in the word");
                lives--;
            }
        }
        if ((new String(mask)).equals(answer)) {
            System.out.println("You guessed the word " + answer + "!");
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}
package Hangman;

import java.util.*;

public class hangman {
    public static void main(String[] args) {
        System.out.println("HANGMAN");
        Scanner sc = new Scanner(System.in);
        List<String> words = Arrays.asList("python", "java", "javascript", "kotlin");
        String answer = words.get(new Random().nextInt(words.size()));
        char[] mask = new char[answer.length()];
        Arrays.fill(mask, '-');
        int lives = 8;
        while (lives > 0 && !(new String(mask)).equals(answer)) {
            System.out.println();
            System.out.println(new String(mask));
            System.out.print("Input a letter: ");
            String s = sc.nextLine();
            if (s.isEmpty()) continue;
            char ch = s.charAt(0);
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
            } else if (!improved) {
                System.out.println("No improvements");
                lives--;
            }
        }
        if ((new String(mask)).equals(answer)) {
            System.out.println("You guessed the word!");
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}
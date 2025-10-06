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
        for (int tries = 0; tries < 8; tries++) {
            System.out.println();
            System.out.println(new String(mask));
            System.out.print("Input a letter: ");
            String s = sc.nextLine();
            if (s.isEmpty()) continue;
            char ch = s.charAt(0);
            boolean opened = false;
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == ch) {
                    if (mask[i] != ch) { mask[i] = ch; opened = true; }
                }
            }
            if (!opened) {
                System.out.println("That letter doesn't appear in the word");
            }
        }
        System.out.println();
        System.out.println("Thanks for playing!");
        System.out.println("We'll see how well you did in the next stage");
    }
}
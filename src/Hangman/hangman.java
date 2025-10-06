package Hangman;

import java.util.*;

public class hangman {
    public static void main(String[] args) {
        System.out.println("HANGMAN");
        Scanner sc = new Scanner(System.in);
        List<String> words = Arrays.asList("python", "java", "javascript", "kotlin");
        String answer = words.get(new Random().nextInt(words.size()));
        System.out.print("Guess the word: ");
        String guess = sc.nextLine();
        if (answer.equals(guess)) {
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}
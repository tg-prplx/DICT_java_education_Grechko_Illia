package Hangman;

import java.util.Scanner;

public class hangman {
    public static void main(String[] args) {
        System.out.println("HANGMAN");
        Scanner sc = new Scanner(System.in);
        System.out.print("Guess the word: ");
        String guess = sc.nextLine();
        String answer = "java";
        if (answer.equals(guess)) {
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}
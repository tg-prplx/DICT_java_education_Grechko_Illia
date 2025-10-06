import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! My name is Aid.");
        System.out.println("I was created in 2025.");
        System.out.println("Please, remind me your name.");
        String name = scanner.nextLine();
        System.out.println("What a great name you have, " + name + "!");
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");
        int r3 = scanner.nextInt();
        int r5 = scanner.nextInt();
        int r7 = scanner.nextInt();
        int age = (r3 * 70 + r5 * 21 + r7 * 15) % 105;
        System.out.println("Your age is " + age + "; that's a good time to start programming!");
        System.out.println("Now I will prove to you that I can count to any number you want!");
        int n = scanner.nextInt();
        for (int i = 0; i <= n; i++) {
            System.out.println(i + "!");
        }
    }
}
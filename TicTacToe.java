package TicTacToe;

import java.util.Scanner;

public class TicTacToe {
    private static final int N = 3;

    public static void main(String[] args) {
        char[][] board = new char[N][N];
        fill(board, ' ');
        printBoard(board);

        char player = 'X';
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the coordinates: ");
            String line = sc.nextLine().trim();

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int a, b;
            try {
                a = Integer.parseInt(parts[0]);
                b = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (a < 1 || a > 3 || b < 1 || b > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            int row = a - 1;
            int col = b - 1;

            if (board[row][col] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            board[row][col] = player;
            printBoard(board);

            char w = winner(board);
            if (w == 'X') {
                System.out.println("X wins");
                break;
            } else if (w == 'O') {
                System.out.println("O wins");
                break;
            } else if (full(board)) {
                System.out.println("Draw");
                break;
            }

            player = (player == 'X') ? 'O' : 'X';
        }
    }

    private static void fill(char[][] b, char c) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) b[i][j] = c;
        }
    }

    private static void printBoard(char[][] b) {
        System.out.println("---------");
        for (int i = 0; i < N; i++) {
            System.out.print("| ");
            for (int j = 0; j < N; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static boolean full(char[][] b) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) if (b[i][j] == ' ') return false;
        }
        return true;
    }

    private static char winner(char[][] b) {
        for (int i = 0; i < N; i++) {
            if (b[i][0] != ' ' && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return b[i][0];
            if (b[0][i] != ' ' && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return b[0][i];
        }
        if (b[0][0] != ' ' && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return b[0][0];
        if (b[2][0] != ' ' && b[2][0] == b[1][1] && b[1][1] == b[0][2]) return b[2][0];
        return ' ';
    }
}

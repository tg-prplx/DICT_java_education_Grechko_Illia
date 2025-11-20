package MatrixProcessing;

import java.util.*;

public class MatrixProcessing {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                return;
            }
            switch (choice) {
                case 1:
                    handleAdd(scanner);
                    break;
                case 2:
                    handleMultiplyByConstant(scanner);
                    break;
                case 3:
                    handleMultiplyMatrices(scanner);
                    break;
                case 4:
                    handleTranspose(scanner);
                    break;
                case 5:
                    handleDeterminant(scanner);
                    break;
                case 6:
                    handleInverse(scanner);
                    break;
                default:
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
    }

    private static void handleAdd(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        int n1 = scanner.nextInt();
        int m1 = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] a = readMatrix(scanner, n1, m1);

        System.out.print("Enter size of second matrix: ");
        int n2 = scanner.nextInt();
        int m2 = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] b = readMatrix(scanner, n2, m2);

        if (n1 != n2 || m1 != m2) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        double[][] res = addMatrices(a, b);
        System.out.println("The result is:");
        printMatrix(res);
    }

    private static void handleMultiplyByConstant(Scanner scanner) {
        System.out.print("Enter size of matrix: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] a = readMatrix(scanner, n, m);
        System.out.print("Enter constant: ");
        double c = scanner.nextDouble();
        double[][] res = multiplyByConstant(a, c);
        System.out.println("The result is:");
        printMatrix(res);
    }

    private static void handleMultiplyMatrices(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        int n1 = scanner.nextInt();
        int m1 = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] a = readMatrix(scanner, n1, m1);

        System.out.print("Enter size of second matrix: ");
        int n2 = scanner.nextInt();
        int m2 = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] b = readMatrix(scanner, n2, m2);

        if (m1 != n2) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        double[][] res = multiplyMatrices(a, b);
        System.out.println("The result is:");
        printMatrix(res);
    }

    private static void handleTranspose(Scanner scanner) {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        int type = scanner.nextInt();

        System.out.print("Enter matrix size: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] a = readMatrix(scanner, n, m);

        double[][] res;
        switch (type) {
            case 1:
                if (n != m) {
                    System.out.println("The operation cannot be performed.");
                    return;
                }
                res = transposeMainDiagonal(a);
                break;
            case 2:
                if (n != m) {
                    System.out.println("The operation cannot be performed.");
                    return;
                }
                res = transposeSideDiagonal(a);
                break;
            case 3:
                res = transposeVertical(a);
                break;
            case 4:
                res = transposeHorizontal(a);
                break;
            default:
                return;
        }
        System.out.println("The result is:");
        printMatrix(res);
    }

    private static void handleDeterminant(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] a = readMatrix(scanner, n, m);
        if (n != m) {
            System.out.println("The operation cannot be performed.");
            return;
        }
        double det = determinant(a);
        System.out.println("The result is:");
        printScalar(det);
    }

    private static void handleInverse(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] a = readMatrix(scanner, n, m);
        if (n != m) {
            System.out.println("The operation cannot be performed.");
            return;
        }
        double[][] inv = inverseMatrix(a);
        if (inv == null) {
            System.out.println("This matrix doesn't have an inverse.");
            return;
        }
        System.out.println("The result is:");
        printMatrix(inv);
    }

    private static double[][] readMatrix(Scanner scanner, int n, int m) {
        double[][] a = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = scanner.nextDouble();
            }
        }
        return a;
    }

    private static double[][] addMatrices(double[][] a, double[][] b) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    private static double[][] multiplyByConstant(double[][] a, double c) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = a[i][j] * c;
            }
        }
        return res;
    }

    private static double[][] multiplyMatrices(double[][] a, double[][] b) {
        int n = a.length;
        int m = b[0].length;
        int kLen = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < kLen; k++) {
                    sum += a[i][k] * b[k][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    private static double[][] transposeMainDiagonal(double[][] a) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[j][i] = a[i][j];
            }
        }
        return res;
    }

    private static double[][] transposeSideDiagonal(double[][] a) {
        int n = a.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = a[n - 1 - j][n - 1 - i];
            }
        }
        return res;
    }

    private static double[][] transposeVertical(double[][] a) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = a[i][m - 1 - j];
            }
        }
        return res;
    }

    private static double[][] transposeHorizontal(double[][] a) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = a[n - 1 - i][j];
            }
        }
        return res;
    }

    private static double determinant(double[][] a) {
        int n = a.length;
        if (n == 1) {
            return a[0][0];
        }
        if (n == 2) {
            return a[0][0] * a[1][1] - a[0][1] * a[1][0];
        }
        double det = 0;
        for (int j = 0; j < n; j++) {
            double[][] minor = minor(a, 0, j);
            double cofactor = ((j % 2 == 0) ? 1 : -1) * a[0][j];
            det += cofactor * determinant(minor);
        }
        return det;
    }

    private static double[][] minor(double[][] a, int row, int col) {
        int n = a.length;
        double[][] res = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) {
                continue;
            }
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) {
                    continue;
                }
                res[r][c] = a[i][j];
                c++;
            }
            r++;
        }
        return res;
    }

    private static double[][] inverseMatrix(double[][] a) {
        int n = a.length;
        double[][] aug = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aug[i][j] = a[i][j];
            }
            for (int j = 0; j < n; j++) {
                aug[i][n + j] = (i == j) ? 1 : 0;
            }
        }

        for (int i = 0; i < n; i++) {
            int pivotRow = i;
            for (int r = i; r < n; r++) {
                if (Math.abs(aug[r][i]) > Math.abs(aug[pivotRow][i])) {
                    pivotRow = r;
                }
            }
            if (Math.abs(aug[pivotRow][i]) < EPS) {
                return null;
            }
            if (pivotRow != i) {
                double[] tmp = aug[i];
                aug[i] = aug[pivotRow];
                aug[pivotRow] = tmp;
            }
            double pivot = aug[i][i];
            for (int j = 0; j < 2 * n; j++) {
                aug[i][j] /= pivot;
            }
            for (int r = 0; r < n; r++) {
                if (r == i) {
                    continue;
                }
                double factor = aug[r][i];
                for (int j = 0; j < 2 * n; j++) {
                    aug[r][j] -= factor * aug[i][j];
                }
            }
        }

        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(aug[i], n, inv[i], 0, n);
        }
        return inv;
    }

    private static void printMatrix(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (j > 0) {
                    System.out.print(" ");
                }
                printNumberInline(a[i][j]);
            }
            System.out.println();
        }
    }

    private static void printNumberInline(double value) {
        double v = value;
        if (Math.abs(v) < EPS) {
            v = 0;
        }
        if (Math.abs(v - Math.round(v)) < EPS) {
            System.out.print(String.format(Locale.US, "%d", Math.round(v)));
        } else {
            String s = String.format(Locale.US, "%.2f", v);
            s = s.replaceAll("\\.?0+$", "");
            System.out.print(s);
        }
    }

    private static void printScalar(double value) {
        double v = value;
        if (Math.abs(v) < EPS) {
            v = 0;
        }
        if (Math.abs(v - Math.round(v)) < EPS) {
            System.out.println(String.format(Locale.US, "%d", Math.round(v)));
        } else {
            String s = String.format(Locale.US, "%.2f", v);
            s = s.replaceAll("\\.?0+$", "");
            System.out.println(s);
        }
    }
}

import java.util.Scanner;

public class FactorialRecursion {

       public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers!");
        } else if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----- Factorial Calculation using Recursion -----");
        System.out.print("Enter a number: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter an integer.");
        } else {
            int number = scanner.nextInt();

            try {
                long result = factorial(number);
                System.out.println("Factorial of " + number + " = " + result);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

      }
}

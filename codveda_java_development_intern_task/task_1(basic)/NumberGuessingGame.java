import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numberToGuess = random.nextInt(100) + 1;
        int attempts = 0;
        int maxAttempts = 7; 
        boolean guessed = false;

        System.out.println("----- Number Guessing Game -----");
        System.out.println("I have picked a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts to guess it!");

        while (attempts < maxAttempts && !guessed) {
            System.out.print("Enter your guess: ");

            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int guess = scanner.nextInt();
            attempts++;

            if (guess == numberToGuess) {
                guessed = true;
                System.out.println("?? Congratulations! You guessed the number in " + attempts + " attempts.");
            } else if (guess < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }
        }

        if (!guessed) {
            System.out.println("?? Sorry! You've used all " + maxAttempts + " attempts.");
            System.out.println("The number was: " + numberToGuess);
        }

           }
}

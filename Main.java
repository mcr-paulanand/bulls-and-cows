package bullscows;

import java.util.*;

public class Main {
    private static final String POSSIBLE_SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        int turns = 0;
        String userGuess;
        int numberOfBulls;
        int numberOfCows;

        int secretCodeLength = getSecretCodeLength();
        int numberOfSymbols = getNumberOfPossibleSymbols();

        checkIfInputIsInvalid(secretCodeLength,numberOfSymbols);

        String secretCode = generateSecretCode(secretCodeLength, numberOfSymbols);

        printWelcomeMessage();
        do {
            turns++;

            System.out.println("Turn " + turns + ":");
            userGuess = getUserGuess();

            numberOfBulls = getNumberOfBulls(secretCode, userGuess);
            numberOfCows = getNumberOfCows(secretCode, userGuess) - numberOfBulls;
            printGrade(numberOfBulls, numberOfCows);
        } while (isSecretNotGuessed(numberOfBulls, secretCodeLength));
    }

    private static int getSecretCodeLength() {
        System.out.println("Input the length of the secret code:");
        String input = new Scanner(System.in).nextLine();
        int length = 0;

        try {
            length = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
            System.exit(1);
        }

        return length;
    }

    private static int getNumberOfPossibleSymbols() {
        System.out.println("Input the number of possible symbols in the code:");
        String input = new Scanner(System.in).nextLine();
        int numberOfSymbols = 0;

        try {
            numberOfSymbols = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
            System.exit(1);
        }

        return numberOfSymbols;
    }

    private static void checkIfInputIsInvalid(int length, int numberOfSymbols) {
        if (length <= 0) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + ".");
            System.exit(1);
        }

        if (numberOfSymbols <= 0) {
            System.out.println("Error: it's not possible to generate a code with " +
                    numberOfSymbols + "unique symbols.");
            System.exit(1);
        }

        if (length > numberOfSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    length + " with " + numberOfSymbols + "unique symbols.");
            System.exit(1);
        }

        if (numberOfSymbols > POSSIBLE_SYMBOLS.length()) {
            System.out.println("Error: maximum number of possible symbols in the code is " +
                    POSSIBLE_SYMBOLS.length() + " (" +
                    POSSIBLE_SYMBOLS.charAt(0) + "-" + POSSIBLE_SYMBOLS.charAt(9) + ", " +
                    POSSIBLE_SYMBOLS.charAt(10) + "-" + POSSIBLE_SYMBOLS.charAt(POSSIBLE_SYMBOLS.length() - 1) + ").");
            System.exit(1);
        }
    }

    private static boolean isSecretNotGuessed (int numberOfBulls, int secretCodeLength) {
        if (numberOfBulls == secretCodeLength) {
            System.out.println("Congratulations! You guessed the secret code.");
            return false;
        } else return true;
    }

    private static String getUserGuess() {
        return new Scanner(System.in).nextLine();
    }

    private static int getNumberOfBulls(String secretCode, String userGuess) {
        int numberOfBulls = 0;
        char[] secretCodeArray = secretCode.toCharArray();
        char[] userGuessArray = userGuess.toCharArray();

        for (int i = 0; i < userGuessArray.length; i++) {
            if (userGuessArray[i] == secretCodeArray[i]) numberOfBulls++;
        }

        return numberOfBulls;
    }

    private static int getNumberOfCows(String secretCode, String userGuess) {
        int numberOfCows = 0;
        String[] userGuessChar = userGuess.split("");

        for (String ch : userGuessChar) {
            if (secretCode.contains(ch)) numberOfCows++;
        }

        return numberOfCows;
    }

    private static void printWelcomeMessage() {
        System.out.println("Okay, let's start a game!");
    }

    private static void printGrade(int numberOfBulls, int numberOfCows) {
        if (numberOfBulls > 0 && numberOfCows > 0) {
            System.out.println("Grade: " + numberOfBulls + " bull(s) and " + numberOfCows + " cow(s)");
        } else if (numberOfBulls > 0) {
            System.out.println("Grade: " + numberOfBulls + " bull(s)");
        } else if (numberOfCows > 0) {
            System.out.println("Grade: " + numberOfCows + " cow(s)");
        } else System.out.println("Grade: None");
    }

    private static void printPossibleCharacters(int length, int numberOfSymbols) {
        System.out.print("The secret is prepared: " + "*".repeat(length) + " (");
        if (numberOfSymbols <= 10) {
            System.out.print(POSSIBLE_SYMBOLS.charAt(0) + "-" + POSSIBLE_SYMBOLS.charAt(numberOfSymbols - 1));
        } else {
            System.out.print(POSSIBLE_SYMBOLS.charAt(0) + "-" + POSSIBLE_SYMBOLS.charAt(9) +
                    ", " + POSSIBLE_SYMBOLS.charAt(10) + "-" + POSSIBLE_SYMBOLS.charAt(numberOfSymbols - 1));
        }

        System.out.println(").");
    }

    private static String generateSecretCode(int length, int numberOfSymbols) {
        List<String> randomList = new ArrayList<>(List.of(POSSIBLE_SYMBOLS.split("")).subList(0,numberOfSymbols));

        Collections.shuffle(randomList);
        StringBuilder secretCode = new StringBuilder();
        for (var ch : randomList.subList(0, length)) {
            secretCode.append(ch);
        }

        printPossibleCharacters(length,numberOfSymbols);
        return secretCode.toString();
    }
}
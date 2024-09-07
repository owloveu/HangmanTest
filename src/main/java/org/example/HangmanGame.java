package org.example;


import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class HangmanGame {
    private final String wordToGuess;
    private final StringBuilder currentGuess;
    private int errorCount;
    private final int maxErrors;
    private final char[] hangmanStages;
    private final Set<Character> guessedLetters;

    public HangmanGame() {
        try {this.wordToGuess = Objects.requireNonNull(Words.getRandomWordFromFile()).toLowerCase();} catch (IOException e) {throw new RuntimeException(e);}
        this.currentGuess = new StringBuilder("_".repeat(wordToGuess.length()));
        this.errorCount = 0;
        this.maxErrors = 7;
        this.hangmanStages = new char[maxErrors];
        for (int i = 0; i < maxErrors; i++) {
            updateHangmanStages();
        }
        this.guessedLetters = new HashSet<>();
    }

    public static char getGuessLetter() {
        Scanner scanner = new Scanner(System.in);
        String regex = "^[а-я]$";
        Pattern pattern = Pattern.compile(regex);
        System.out.print("Введите букву: ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (pattern.matcher(input).matches()) {
            return input.charAt(0);
        } else {
            System.out.println("Неверный ввод. Пожалуйста, введите одну букву.");
            return getGuessLetter();
        }
    }

    public void makeGuess(char getGuessLetter) {
        getGuessLetter = Character.toLowerCase(getGuessLetter);
        if (guessedLetters.contains(getGuessLetter)) {
            System.out.println("\nЭти буквы уже были: " + guessedLetters +"\nВведите другую: ");
            return;
        }
        guessedLetters.add(getGuessLetter);
        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == getGuessLetter) {
                currentGuess.setCharAt(i, getGuessLetter);
                correctGuess = true;
            }
        }
        if (!correctGuess) {
            errorCount++;
            updateHangmanStages();
        }
        printCurrentState();
    }

    private void updateHangmanStages() {
        switch (errorCount) {
            case 1: System.out.println("  \\\n" +
                    "\n" +
                    "\n" +
                    "\n");
                break;
            case 2: System.out.println("  \\\n" +
                    "   ☻\n" +
                    "\n" +
                    "\n");
                break;
            case 3: System.out.println("  \\\n" +
                    "  ☻\n" +
                    "/\n" +
                    "\n");
                break;
            case 4: System.out.println("  \\\n" +
                    "  ☻\n" +
                    "/ ▲\n" +
                    "\n");
                break;
            case 5: System.out.println("  \\\n" +
                    "  ☻\n" +
                    "/ ▲ \\\n" +
                    "\n");
                break;
            case 6: System.out.println("  \\\n" +
                    "  ☻\n" +
                    "/ ▲ \\\n" +
                    " |\n");
                break;
            case 7: System.out.println("  \\\n" +
                    "  ☻\n" +
                    "/ ▲ \\\n" +
                    " | |\n");
                break;
        }
    }

    private void printCurrentState() {
        System.out.println(currentGuess);
        System.out.println("\nКоличество ошибок: " + errorCount);
    }

    public boolean isGameOver() {return errorCount >= maxErrors || currentGuess.indexOf("_") == -1;}

    public boolean isWin() {return currentGuess.indexOf("_") == -1;}


    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            char guessedLetter = getGuessLetter();
            makeGuess(guessedLetter);
            updateHangmanStages();
            if (isGameOver()) {
                if (isWin()) {
                    System.out.println("\nПоздравляем! Вы угадали слово: " + wordToGuess.toUpperCase());
                    Menu.menu();
                } else {
                    System.out.println("\nВы проиграли. Слово было: " + wordToGuess.toUpperCase());
                    Menu.menu();
                }
            }
        }
        scanner.close();
    }
}

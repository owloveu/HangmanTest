package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    public static void menu() {
        System.out.println("\nНовая игра / Выход \nВведите Н или В");
        switch (getMenuCommand()) { // Ожидается ввод команды (N или E)
            case 'Н':
            case 'н':
                newGame();
                break;
            case 'В':
            case 'в':
                exit();
                break;
            default:
                newGame();
                break;
        }
    }

    static char getMenuCommand() {
        Scanner scanner = new Scanner(System.in);
        String regex = "^[нвНВ]$";
        Pattern pattern = Pattern.compile(regex);
        String input = scanner.nextLine().trim();
        if (pattern.matcher(input).matches()) {
            return input.charAt(0);
        } else {
            System.out.println("Неверный ввод. Пожалуйста, введите 'н' или 'в'.");
            return getMenuCommand();
        }
    }

    static void newGame() {
        System.out.println("Начинаем новую игру!");
        HangmanGame game = new HangmanGame();
        game.startGame();
    }

    static void exit() {
        System.out.println("Выход из игры...");
        System.exit(0);
    }
}

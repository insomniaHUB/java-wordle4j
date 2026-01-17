package ru.yandex.practicum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordList();
        WordleGame game = new WordleGame(dictionary);
        String continueGame;

        while (true) {
            try {
                System.out.println("Введите слово: ");
                String guess = scanner.nextLine().trim().toLowerCase();
                if (guess.length() == 5 || guess.isEmpty()) {
                    continueGame = game.startGame(guess);
                } else {
                    System.out.println("Введите правильное слово!");
                    continue;
                }

                if (guess.isEmpty() && continueGame.length() == 10 && !continueGame.startsWith("+")) {
                    System.out.println("Использовано слово: " + continueGame.substring(0, 5));
                    System.out.println(continueGame.substring(5));
                } else if (continueGame.isEmpty()) {
                    System.out.println("Нет подходящих слов");
                } else if (continueGame.substring(0, 5).equals("+++++")) {
                    if (guess.isEmpty()) {
                        System.out.println(continueGame.substring(10));
                        System.out.println("+++++");
                        System.out.println("Слово отгадано! Это было слово - " + continueGame.substring(5, 10));
                        break;
                    } else {
                        System.out.println("Слово отгадано! Это было слово - " + continueGame.substring(5));
                        break;
                    }
                } else if (continueGame.startsWith("Ходы закончились!")) {
                    System.out.println("Игра окончена! " + continueGame.substring(0, 17)
                            + " Это было слово - " + continueGame.substring(17));
                    break;
                } else {
                    System.out.println(continueGame);
                }
            } catch (NotCorrectLanguageWord e) {
                System.out.println("Пожалуйста, введите слово на русском языке.");
                log(e.getMessage());
            } catch (WordNotFoundInDictionary e) {
                System.out.println("Данного слова нет в словаре! Пожалуйста, введите другое слово.");
                log(e.getMessage());
            } catch (Exception e) {
                log(e.getMessage());
            }
        }
        log("Игра окончена.");
    }


    public static void log(String message) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("game.log", true)))) {
            printWriter.println(message);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }
}

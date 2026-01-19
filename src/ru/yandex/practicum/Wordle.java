package ru.yandex.practicum;

import exception.NotCorrectLanguageWord;
import exception.WordNotFoundInDictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static ru.yandex.practicum.WordleDictionary.normalize;

public class Wordle {
    public static final int APPROPRIATE_LENGTH = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordList();
        WordleGame game = new WordleGame(dictionary);
        String continueGame;
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("game.log", true)))) {
            while (true) {
                try {
                    System.out.println("Введите слово: ");
                    String guess = normalize(scanner.nextLine());
                    if (guess.length() == APPROPRIATE_LENGTH || guess.isEmpty()) {
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
                            printWriter.println("Слово отгадано! Это было слово - " + continueGame.substring(5, 10));
                            break;
                        } else {
                            System.out.println("Слово отгадано! Это было слово - " + continueGame.substring(5));
                            printWriter.println("Слово отгадано! Это было слово - " + continueGame.substring(5, 10));
                            break;
                        }
                    } else if (continueGame.startsWith("Ходы закончились!")) {
                        System.out.println("Игра окончена! " + continueGame.substring(0, 17)
                                + " Это было слово - " + continueGame.substring(17));
                        printWriter.println("Ходы закончились! Игра окончена!");
                        break;
                    } else {
                        System.out.println(continueGame);
                    }
                } catch (NotCorrectLanguageWord e) {
                    System.out.println("Пожалуйста, введите слово на русском языке.");
                    printWriter.println(e.getMessage());
                } catch (WordNotFoundInDictionary e) {
                    System.out.println("Данного слова нет в словаре! Пожалуйста, введите другое слово.");
                    printWriter.println(e.getMessage());
                } catch (Exception e) {
                    printWriter.println(e.getMessage());
                }
            }
            printWriter.println("Игра окончена.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }

    }
}

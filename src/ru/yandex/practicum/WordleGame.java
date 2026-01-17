package ru.yandex.practicum;
import java.util.*;

import static ru.yandex.practicum.Wordle.log;

public class WordleGame {
    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    public List<String> clue = new ArrayList<>();
    private Set<Character> nonCorrectLetters = new HashSet<>();
    private Set<Character> wrongPositionLetter = new HashSet<>();
    char[] positions = new char[5];
    private List<String> wordsToRemove = new ArrayList<>();
    public List<String> enteredWords = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = getFirstTimeAnswer();
        this.steps = 6;
        this.clue = new ArrayList<>(dictionary.getWords());
        Arrays.fill(positions, '0');
    }

    public String startGame(String check) throws WordNotFoundInDictionary, NotCorrectLanguageWord {
        StringBuilder result;
        String guess = toRightWord(check);

        if (!guess.matches("^[а-яА-ЯёЁ]+$") && !check.isEmpty()) {
            throw new NotCorrectLanguageWord("Введено слово с некорректными символами.");
        }

        if (!dictionary.getWords().contains(guess) && !check.isEmpty()) {
            throw new WordNotFoundInDictionary("Словарь не содержит данного слова.");
        }

        if (steps != 0) {
            if (guess.isBlank()) {

                for (String word : clue) {
                    if (enteredWords.contains(word)) {
                        wordsToRemove.add(word);
                    }
                }
                clue.removeAll(wordsToRemove);

                wordsToRemove = new ArrayList<>();
                for (String word : clue) {
                    for (int j = 0; j < 5; j++) {
                        if (nonCorrectLetters.contains(word.charAt(j))) {
                            wordsToRemove.add(word);
                            break;
                        }
                    }
                }
                clue.removeAll(wordsToRemove);

                wordsToRemove = new ArrayList<>();
                for (String word : clue) {
                    for (int i = 0; i < 5; i++) {
                        if (positions[i] != '0' && word.charAt(i) != positions[i]) {
                            wordsToRemove.add(word);
                            break;
                        }
                    }
                }
                clue.removeAll(wordsToRemove);

                wordsToRemove = new ArrayList<>();
                for (String word : clue) {
                    for (Character letter : wrongPositionLetter) {
                        if (word.indexOf(letter) == -1) {
                            wordsToRemove.add(word);
                            break;
                        }
                    }
                }
                clue.removeAll(wordsToRemove);
                if (!clue.isEmpty()) {
                    guess = getClue();
                    clue.remove(guess);
                } else {
                    return "";
                }
            }

            if (!enteredWords.contains(guess)) {
                enteredWords.add(guess);
            }

            if (guess.equals(answer)) {
                log("Слово отгадано!");
                if (check.isEmpty()) {
                    return "+++++" + answer + guess;
                }
                return "+++++" + answer;
            }

            result = new StringBuilder("-----");
            StringBuilder answerCopy = new StringBuilder(answer);
            for (int i = 0; i < 5; i++) {
                if (guess.charAt(i) == answerCopy.charAt(i)) {
                    result.replace(i, i + 1, "+");
                    answerCopy.replace(i, i + 1, "*");
                    positions[i] = guess.charAt(i);
                    wrongPositionLetter.add(guess.charAt(i));
                }
            }
            for (int i = 0; i < 5; i++) {
                if (result.charAt(i) == '+') {
                    continue;
                }
                for (int j = 0; j < 5; j++) {
                    if (answerCopy.charAt(j) == guess.charAt(i)) {
                        result.replace(i, i + 1, "^");
                        answerCopy.replace(j, j + 1, "*");
                        wrongPositionLetter.add(guess.charAt(i));
                        break;
                    }
                }
            }

            Set<Character> letters = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                if (result.charAt(i) == '+' || result.charAt(i) == '^') {
                    letters.add(guess.charAt(i));
                }
            }

            for (int i = 0; i < 5; i++) {
                if (result.charAt(i) == '-') {
                    char currentChar = guess.charAt(i);
                    boolean usedLetter = false;
                    for (int j = 0; j < 5; j++) {
                        if (j != i && guess.charAt(j) == currentChar && (result.charAt(j) == '+' || result.charAt(j) == '^')) {
                            usedLetter = true;
                            break;
                        }
                    }
                    if (!usedLetter && !letters.contains(currentChar)) {
                        nonCorrectLetters.add(currentChar);
                    }
                }
            }


            String resultStr = result.toString();
            if (steps != 0) {
                steps--;
                log("Ходов осталось: " + steps);
            }

            if (check.isEmpty()) {
                return guess + resultStr;
            }
            return resultStr;
        } else {
            return "Ходы закончились!" + answer;
        }
    }

    public String toRightWord(String word) {
        StringBuilder strBuilder = new StringBuilder(word);
        if (strBuilder.length() == 5) {
            for (int i = 0; i < strBuilder.length(); i++) {
                if (strBuilder.charAt(i) == 'ё') {
                    strBuilder.replace(i, i + 1, "е");
                }
            }
        }
        return strBuilder.toString();
    }

    public String getClue() {
        Random random = new Random();
        int randomIndex = random.nextInt(clue.size());
        return clue.get(randomIndex);
    }

    public String getFirstTimeAnswer() {
        Random random = new Random();
        int randomIndex = random.nextInt(dictionary.getWords().size());
        return dictionary.getWords().get(randomIndex);
    }

    public String getAnswer() {
        return answer;
    }

}

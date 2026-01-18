package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.Wordle.appropriateLength;

public class WordleDictionary {

    private final List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        if (word.length() == appropriateLength) {
            word = normalize(word.toLowerCase());
            words.add(word);
        }
    }

    public static String normalize(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'ё') {
                word = word.replace("ё", "е");
            }
        }
        return word;
    }

}

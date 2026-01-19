package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.Wordle.APPROPRIATE_LENGTH;

public class WordleDictionary {

    private final List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        if (word.length() == APPROPRIATE_LENGTH) {
            word = normalize(word.toLowerCase());
            words.add(word);
        }
    }

    public static String normalize(String word) {
        word = word.trim().toLowerCase();
        if (word.contains("ё")) {
            word = word.replace("ё", "е");
        }
        return word;
    }

}

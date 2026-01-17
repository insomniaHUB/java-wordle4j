package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private final List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        StringBuilder strBuilder = new StringBuilder(word.toLowerCase());
        if (word.length() == 5) {
            for (int i = 0; i < word.length(); i++) {
                if (strBuilder.charAt(i) == 'ё') {
                    strBuilder.replace(i, i + 1, "е");
                }
            }
            word = strBuilder.toString();
            words.add(word);
        }
    }

}

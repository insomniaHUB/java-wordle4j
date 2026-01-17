package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import static ru.yandex.practicum.Wordle.log;

public class WordleDictionaryLoader {

    public WordleDictionary loadWordList() {
        WordleDictionary dictionary = new WordleDictionary();
        try (FileReader reader = new FileReader("words_ru.txt", StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                dictionary.addWord(line);
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return dictionary;
    }


}

package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.WordleDictionary.normalize;

class WordleTest {

    @Test
    void correctAddToDictionaryTest() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.addWord("мёд");
        dictionary.addWord("Мышка");
        dictionary.addWord("БЕЛЬЁ");

        Assertions.assertEquals(2, dictionary.getWords().size());
        Assertions.assertEquals("белье", dictionary.getWords().get(1));
        Assertions.assertEquals('е', dictionary.getWords().get(1).charAt(4));
    }

    @Test
    void loadOfDictionaryTest() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordList();

        Assertions.assertFalse(dictionary.getWords().isEmpty());
    }

    @Test
    void correctProcessingOfWordsTest() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordList();
        WordleGame game = new WordleGame(dictionary);
        String str = game.startGame("").substring(0, 5);

        assertEquals("белье", normalize("бельё"));
        assertTrue(dictionary.getWords().contains(game.getClue()));
        assertTrue(dictionary.getWords().contains(game.getAnswer()));
        assertEquals("+++++" + game.getAnswer(), game.startGame(game.getAnswer()));
        assertTrue(dictionary.getWords().contains(str) && game.getEnteredWords().contains(str)
                && !game.getClueList().contains(str));
    }

}

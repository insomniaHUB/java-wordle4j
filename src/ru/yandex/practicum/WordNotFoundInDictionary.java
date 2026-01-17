package ru.yandex.practicum;

public class WordNotFoundInDictionary extends RuntimeException {

    public WordNotFoundInDictionary() {
        super();
    }

    public WordNotFoundInDictionary(String message) {
        super(message);
    }
}

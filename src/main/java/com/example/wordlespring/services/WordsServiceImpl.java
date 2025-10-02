package com.example.wordlespring.services;

import com.example.wordlespring.repositories.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@SessionScope
@Service
public class WordsServiceImpl implements WordsService {

    private final WordsRepository wordsRepository;
    private final List<String> words;

    @Autowired
    public WordsServiceImpl(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
        this.words = new ArrayList<>();
    }

    @Override
    public List<String> getWords() {
        if (words.isEmpty()) {
            words.addAll(wordsRepository.getAllWordsList());
        }
        return words;
    }

    @Override
    public void getWordsWithOutChar(String ch) {
        words.removeIf(word -> word.contains(ch));
    }

    @Override
    public void getWordsLeaveChar(String ch) {
        words.removeIf(word -> !word.contains(ch));
    }

    @Override
    public void resetWordsList() {
        words.clear();
    }
}

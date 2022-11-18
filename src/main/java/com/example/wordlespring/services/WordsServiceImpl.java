package com.example.wordlespring.services;

import com.example.wordlespring.repositories.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordsServiceImpl implements WordsService {

    private final WordsRepository wordsRepository;

    @Autowired
    public WordsServiceImpl(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Override
    public List<String> getWords() {
        return wordsRepository.getAllWordsList();
    }

    @Override
    public void getWordsWithOutChar(String ch) {
        wordsRepository.getWordsWithOutChar(ch);
    }

    @Override
    public void getWordsLeaveChar(String ch) {
        wordsRepository.getWordsWithLeaveChar(ch);
    }

    @Override
    public void resetWordsList() {
        wordsRepository.resetButton();
    }
}

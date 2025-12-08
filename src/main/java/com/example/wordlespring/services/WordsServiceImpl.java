package com.example.wordlespring.services;

import com.example.wordlespring.dto.RequestWordleDto;
import com.example.wordlespring.repositories.WordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SessionScope
@Service
public class WordsServiceImpl implements WordsService {

    private final WordsRepository wordsRepository;
    private final List<String> words;
    private final Logger log = LoggerFactory.getLogger(WordsServiceImpl.class);

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

    @Override
    public List<String> getWordsList(RequestWordleDto request) {
        log.info(request.toString());

        List<String> allWordsList = getWords();
        log.info("All words list is before " + allWordsList.size());

        String excludedLetters = request.excludedLetters();
        if (excludedLetters != null && !excludedLetters.isEmpty()) {
            char[] excludedLettersCharArray = excludedLetters.toCharArray();
            for (char letter : excludedLettersCharArray) {
                allWordsList.removeIf(word -> word.contains(String.valueOf(letter)));
            }
        }

        String requiredLetters = request.requiredLetters();
        if (requiredLetters != null && !requiredLetters.isEmpty()) {
            char[] requiredLettersCharArray = request.requiredLetters().toCharArray();
            for (char letter : requiredLettersCharArray) {
                allWordsList.removeIf(word -> !word.contains(String.valueOf(letter)));
            }
        }

        Map<Integer, Character> map = request.fixedPositions();
        map.forEach((k, v) -> {
            allWordsList.removeIf(word -> word.toCharArray()[k] != map.get(k));
        });

        log.info("All words list is after " + allWordsList.size());

        return allWordsList;
    }
}

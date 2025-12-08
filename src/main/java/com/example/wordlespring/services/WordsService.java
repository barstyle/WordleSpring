package com.example.wordlespring.services;

import com.example.wordlespring.dto.RequestWordleDto;

import java.util.List;

public interface WordsService {

    List<String> getWords();

    void getWordsWithOutChar(String ch);

    void getWordsLeaveChar(String ch);

    void resetWordsList();

    List<String> getWordsList(RequestWordleDto request);

}

package com.example.wordlespring.services;

import java.util.List;

public interface WordsService {

    public List<String> getWords();

    public void getWordsWithOutChar(String ch);

    public void getWordsLeaveChar(String ch);

    public void resetWordsList();

}

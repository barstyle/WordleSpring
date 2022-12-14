package com.example.wordlespring.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class WordsRepository {

    private ArrayList<String> arrayList;

    @Value("${dict}")
    private String dict;

    private ArrayList<String> getList() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(dict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Scanner scanner = new Scanner(fileReader);

            //Добавляем в наш список все слова из файла
            while (scanner.hasNextLine()) {
                arrayList.add(scanner.nextLine());
            }
        }
        return arrayList;
    }

    public List<String> getAllWordsList() {
        return getList();
    }

    public void getWordsWithOutChar(String ch) {
        arrayList.removeIf(word -> word.contains(ch));
    }

    public void getWordsWithLeaveChar(String ch) {
        arrayList.removeIf(word -> !word.contains(ch));
    }

    public void resetButton () {
        arrayList = null;
    }
}

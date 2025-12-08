package com.example.wordlespring.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Repository
public class WordsRepository {

    private ArrayList<String> arrayList;

    @Value("${dict}")
    private String dict;
    @Value("${dict_docker}")
    private String dictDocker;

    private ArrayList<String> getList() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(dict);
            } catch (FileNotFoundException e) {
                log.warn(String.format("Куда-то делся файлик %s", e.getMessage()));
                try {
                    fileReader = new FileReader(dictDocker);
                } catch (FileNotFoundException ex) {
                    log.warn(String.format("Куда-то делся файлик %s", e.getMessage()));
                }
            }

            assert fileReader != null;
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
}

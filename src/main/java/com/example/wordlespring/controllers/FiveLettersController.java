package com.example.wordlespring.controllers;

import com.example.wordlespring.dto.RequestWordleDto;
import com.example.wordlespring.services.WordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class FiveLettersController {

    private final WordsService wordsService;
    private final Logger logger = LoggerFactory.getLogger(FiveLettersController.class);

    @Autowired
    public FiveLettersController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @PostMapping("/words")
    public ResponseEntity<List<String>> getFilterList(
            @RequestBody RequestWordleDto requestWordleDto,
            HttpServletRequest request) {
        logger.info("Session ID - {}", request.getSession().getId());
        List<String> wordsList = wordsService.getWordsList(requestWordleDto);
        return new ResponseEntity<>(wordsList, HttpStatus.OK);
    }

    @DeleteMapping("/words")
    public ResponseEntity<String> resetDictionary() {
        wordsService.resetWordsList();
        logger.info("Reset words list");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

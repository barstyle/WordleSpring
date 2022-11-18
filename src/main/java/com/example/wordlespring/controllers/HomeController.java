package com.example.wordlespring.controllers;

import com.example.wordlespring.services.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final WordsService wordsService;

    @Autowired
    public HomeController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping(value = "/")
    public String homePage (Model model) {
        model.addAttribute("wordsList", wordsService.getWords());
        return "home";
    }

    @GetMapping (value = "/remove-{id}")
    private String removeChar (@PathVariable(value = "id") String ch) {
        wordsService.getWordsWithOutChar(ch);
        return "redirect:/";
    }

    @GetMapping (value = "/leave-{id}")
    private String leaveChar (@PathVariable(value = "id") String ch) {
        wordsService.getWordsLeaveChar(ch);
        return "redirect:/";
    }

    @GetMapping (value = "/reset")
    public String resetButton() {
        wordsService.resetWordsList();
        return "redirect:/";
    }


}

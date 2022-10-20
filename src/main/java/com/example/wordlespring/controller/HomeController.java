package com.example.wordlespring.controller;

import com.example.wordlespring.repo.AllWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final AllWords allWords;

    @Autowired
    public HomeController(AllWords allWords) {
        this.allWords = allWords;
    }

    @GetMapping(value = "/")
    public String homePage (Model model) {
        model.addAttribute("wordsList", allWords.getAllWordsList());
        return "home";
    }

    @GetMapping (value = "/remove-{id}")
    private String removeChar (@PathVariable(value = "id") String ch) {
        allWords.getWordsWithOutChar(ch);
        return "redirect:/";
    }

    @GetMapping (value = "/leave-{id}")
    private String leaveChar (@PathVariable(value = "id") String ch) {
        allWords.getWordsWithLeaveChar(ch);
        return "redirect:/";
    }

    @GetMapping (value = "/reset")
    public String resetButton() {
        allWords.resetButton();
        return "redirect:/";
    }


}

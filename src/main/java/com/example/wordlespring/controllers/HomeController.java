package com.example.wordlespring.controllers;

import com.example.wordlespring.services.WordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    private final WordsService wordsService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public HomeController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping(value = "/")
    public String homePage (Model model, HttpServletRequest request) {
        logger.info("Home page, Session ID - {}", request.getSession().getId());
        model.addAttribute("wordsList", wordsService.getWords());
        double progress = (((double) wordsService.getWords().size() / 4712.0) * 100);
        String progressBar = "width: " + progress + "%;";
        model.addAttribute("progress", progressBar);
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
        logger.info("word has char - {}", ch);
        return "redirect:/";
    }

    @GetMapping (value = "/reset")
    public String resetButton() {
        wordsService.resetWordsList();
        logger.info("Put button RESET");
        return "redirect:/";
    }


}

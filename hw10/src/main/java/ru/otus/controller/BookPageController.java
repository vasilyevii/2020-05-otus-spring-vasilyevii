package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("keywords", "books");
        return "list";
    }
}

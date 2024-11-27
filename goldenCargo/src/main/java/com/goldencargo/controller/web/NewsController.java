package com.goldencargo.controller.web;

import com.goldencargo.model.entities.News;
import com.goldencargo.service.NewsService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllNews(Model model) {
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        return "news/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("users", userService.getAllUsers());
        return "news/create";
    }

    @PostMapping("/create")
    public String createNews(@ModelAttribute News news) {
        newsService.createNews(news);
        return "redirect:/news";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<News> news = newsService.getNewsById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            model.addAttribute("users", userService.getAllUsers());
            return "news/edit";
        }
        return "redirect:/news";
    }

    @PostMapping("/update/{id}")
    public String updateNews(@PathVariable Long id, @ModelAttribute News newsDetails) {
        newsService.updateNews(id, newsDetails);
        return "redirect:/news";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<News> news = newsService.getNewsById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            return "news/details";
        }
        return "redirect:/news";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLogistic(@PathVariable Long id) {
        return newsService.deleteNews(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

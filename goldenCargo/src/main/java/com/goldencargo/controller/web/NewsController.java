package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.News;
import com.goldencargo.service.GenericService;
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

    private static final String ALIAS = "n";

    private final NewsService newsService;
    private final UserService userService;
    private final GenericService genericService;

    public NewsController(NewsService newsService, UserService userService, GenericService genericService) {
        this.newsService = newsService;
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllNews(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "datePosted") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "desc") String sortLogic,
            Model model) {

        List<News> newsList = genericService.getFilteredAndSortedEntities(
                News.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("newsList", newsList);
        model.addAttribute("news", new News());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", Status.values());
        return "news/main";
    }

    @PostMapping("/create")
    public String createNews(News news) {
        newsService.createNews(news);
        return "redirect:/news";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<News> news = newsService.getNewsById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            model.addAttribute("users", userService.getAllUsers());
            return "news/edit";
        }
        return "redirect:/news";
    }

    @PostMapping("/update/{id}")
    public String updateNews(@PathVariable Long id, News newsDetails) {
        newsService.updateNews(id, newsDetails);
        return "redirect:/news";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        Optional<News> news = newsService.getNewsById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            return "news/details";
        }
        return "redirect:/news";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        return newsService.deleteNews(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

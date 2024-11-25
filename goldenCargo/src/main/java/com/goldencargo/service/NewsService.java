package com.goldencargo.service;

import com.goldencargo.component.DateConverter;
import com.goldencargo.model.entities.News;
import com.goldencargo.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private DateConverter converter;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public void createNews(News news) {
        newsRepository.save(news);
    }

    public void updateNews(Long id, News newsDetails) {
        newsRepository.findById(id).map(news -> {
            news.setTitle(newsDetails.getTitle());
            news.setContent(newsDetails.getContent());
            news.setDatePosted(newsDetails.getDatePosted());
            news.setPostedBy(newsDetails.getPostedBy());
            news.setUpdatedAt(new java.util.Date());
            return newsRepository.save(news);
        });
    }

    public boolean deleteNews(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

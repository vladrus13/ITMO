package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void make(Article article) {
        articleRepository.save(article);
    }

    public List<Article> getArticlesByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public Article find(long id) { return articleRepository.find(id); }

    public void hiddenSwap(Article article) { articleRepository.hiddenSwap(article);}
}

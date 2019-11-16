package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    List<Article> findByUserId(long userId);
    List<Article> findAll();
    void save(Article article);
    Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    void hiddenSwap(Article article);
}

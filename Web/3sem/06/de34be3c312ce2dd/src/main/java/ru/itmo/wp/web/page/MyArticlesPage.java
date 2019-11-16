package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
        User user = getUser(request, view);
        if (user == null) {
            request.getSession().setAttribute("message", "You must login!");
            throw new RedirectException("/index");
        }
    }

    private void mark(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser(request, view);
        if (user == null) {
            request.getSession().setAttribute("message", "You must login!");
            throw new RedirectException("/index");
        }
        Article article = articleService.find(Long.parseLong(request.getParameter("id")));
        if (article.getUserId() != user.getId()) {
            request.getSession().setAttribute("message", "You CANT DO THIS!");
            throw new RedirectException("/index");
        }
        articleService.hiddenSwap(article);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser(request, view);
        if (user == null) {
            request.getSession().setAttribute("message", "You must login!");
            throw new RedirectException("/index");
        }
        view.put("posts", articleService.getArticlesByUserId(user.getId()));
    }

    private User getUser(HttpServletRequest request, Map<String, Object> view) {
        return (User) request.getSession().getAttribute("user");
    }
}

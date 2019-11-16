package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You must login!");
            throw new RedirectException("/index");
        }
    }

    private void article(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You must login!");
            throw new RedirectException("/index");
        }
        Article article = new Article();
        article.setText(request.getParameter("text"));
        article.setTitle(request.getParameter("title"));
        article.setUserId(user.getId());
        article.setHidden(false);
        articleService.make(article);
        request.getSession().setAttribute("message", "You are successfully post!");
        throw new RedirectException("/index");
    }
}

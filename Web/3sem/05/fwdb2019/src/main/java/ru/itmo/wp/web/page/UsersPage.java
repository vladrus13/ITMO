package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        super.action(request, view);
    }
}

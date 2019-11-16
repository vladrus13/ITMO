package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        User user = getUser(request, view);
        if (user == null) {
            view.put("logged", null);
        } else {
            user = userService.find(getUser(request, view).getId());
            request.getSession().setAttribute("user", user);
            view.put("logged", user);
        }
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void markAdmin(HttpServletRequest request, Map<String, Object> view) {
        boolean to = Boolean.parseBoolean(request.getParameter("to"));
        User user = userService.find(getUser(request, view).getId());
        request.getSession().setAttribute("user", user);
        if (user == null || !user.getAdmin()) {
            request.getSession().setAttribute("message", "You not admin!");
        }
        user = userService.find(Long.parseLong(request.getParameter("choosen")));
        userService.markAdmin(user, to);
        view.put("logged", getUser(request, view));
    }

    private User getUser(HttpServletRequest request, Map<String, Object> view) {
        return (User) request.getSession().getAttribute("user");
    }
}

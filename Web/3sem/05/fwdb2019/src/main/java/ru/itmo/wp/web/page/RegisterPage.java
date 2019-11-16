package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class RegisterPage extends Page {
    private final UserService userService = new UserService();

    private void register(HttpServletRequest request) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirm = request.getParameter("passwordConfirmation");
        String email = request.getParameter("email");
        userService.validateRegistration(user, email, password, confirm);
        userService.register(user, password, email);

        request.getSession().setAttribute("message", "You are successfully registered!");
        throw new RedirectException("/index");
    }
}

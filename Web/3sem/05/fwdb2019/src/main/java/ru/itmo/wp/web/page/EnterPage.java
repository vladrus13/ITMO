package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class EnterPage extends Page {
    private final UserService userService = new UserService();
    private final EventRepository eventRepository = new EventRepository();

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        User user = userService.validateAndFindByLoginAndPassword(loginOrEmail, password);
        request.getSession().setAttribute("user", user);
        setMessage(request, view, "Hello, " + user.getLogin());
        Event event = new Event();
        event.setTypeEvent("ENTER");
        eventRepository.save(event, user);
        throw new RedirectException("/index");
    }
}

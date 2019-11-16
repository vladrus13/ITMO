package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class LogoutPage extends Page {
    EventRepository eventRepository = new EventRepository();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        User user = (User) request.getSession().getAttribute("user");
        Event event = new Event();
        event.setTypeEvent("LOGOUT");
        eventRepository.save(event, user);
        request.getSession().removeAttribute("user");
        setMessage(request, view, "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}

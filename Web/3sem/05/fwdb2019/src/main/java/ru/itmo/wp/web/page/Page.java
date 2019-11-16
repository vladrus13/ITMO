package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {

    private UserService userService = new UserService();

    void before(HttpServletRequest request, Map<String, Object> view) {
        putUser(request, view);
        view.put("countUser", userService.findCount());
    }

    void after(HttpServletRequest request, Map<String, Object> view) {

    }

    void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    protected void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(HttpServletRequest request, Map <String, Object> view, String message) {
        request.getSession().setAttribute("message", message);
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser(request, view);
        if (user != null) {
            view.put("user", user);
        }
    }

    protected User getUser(HttpServletRequest request, Map<String, Object> view) {
        return (User) request.getSession().getAttribute("user");
    }
}

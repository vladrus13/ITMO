package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    TalkRepository talkRepository = new TalkRepository();
    UserService userService = new UserService();

    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = getUser(request, view);
        if (user == null) {
            setMessage(request, view, "You must be enter!");
            throw new RedirectException("/index");
        } else {
            view.put("talks", talkRepository.findAllById(user.getId()));
            view.put("users", userService.findAll());
        }
    }

    void talk(HttpServletRequest request, Map<String, Object> view) {
        Talk talk = new Talk();
        User user = getUser(request, view);
        String text = request.getParameter("text");
        User select = userService.getUserByLogin(request.getParameter("select"));
        talk.setText(text);
        talk.setTargetUserId(select.getId());
        talk.setSourceUserId(user.getId());
        talkRepository.save(talk, user, select);
        throw new RedirectException("/talks");
    }
}

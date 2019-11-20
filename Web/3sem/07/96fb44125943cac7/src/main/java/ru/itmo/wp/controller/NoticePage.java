package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("notice")
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("")
    public String registerGet(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "NoticePage";
    }

    @PostMapping("")
    public String registerPost(@Valid @ModelAttribute("noticeForm")NoticeForm noticeForm,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.register(noticeForm);
        putMessage(httpSession, "Congrats, you make post!");
        return "redirect:/";
    }
}

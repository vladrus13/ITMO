package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserEnterCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UsersController extends ApiController {
    private final UserService userService;
    private final JwtService jwtService;

    public UsersController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("users")
    public List<User> findUsers() {return userService.getAll(); }

    @PostMapping("users")
    public String register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        userService.register(userRegisterCredentials);
        return jwtService.create(userRegisterCredentials.getLogin(), userRegisterCredentials.getPassword());
    }
}

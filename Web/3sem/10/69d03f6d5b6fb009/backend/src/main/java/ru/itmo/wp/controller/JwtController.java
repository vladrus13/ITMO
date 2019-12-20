package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserEnterCredentials;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1")
public class JwtController extends ApiController {
    private final JwtService jwtService;
    private final UserCredentialsEnterValidator userCredentialsEnterValidator;

    public JwtController(JwtService jwtService, UserCredentialsEnterValidator userCredentialsEnterValidator) {
        this.jwtService = jwtService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsEnterValidator);
    }

    @PostMapping("jwt")
    public String createJwt(@RequestBody @Valid UserEnterCredentials userEnterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        return jwtService.create(userEnterCredentials.getLogin(), userEnterCredentials.getPassword());
    }
}

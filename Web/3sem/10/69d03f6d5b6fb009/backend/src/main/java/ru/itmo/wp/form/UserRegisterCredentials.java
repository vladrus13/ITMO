package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24)
    @Pattern(regexp = "[a-z]+", message = "Expected lowercase Latin letters")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 32)
    private String name;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

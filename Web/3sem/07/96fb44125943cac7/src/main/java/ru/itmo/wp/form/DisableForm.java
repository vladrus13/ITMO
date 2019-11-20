package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;

public class DisableForm {

    @NotNull
    private long id;
    private boolean disable;

    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

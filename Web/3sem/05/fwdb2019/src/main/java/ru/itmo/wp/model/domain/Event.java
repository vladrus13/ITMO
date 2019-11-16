package ru.itmo.wp.model.domain;

import java.util.Date;

enum TypeEvent {
    ENTER("ENTER"), LOGOUT("LOGOUT");

    TypeEvent(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
};

public class Event {
    private long id;
    private long userId;
    TypeEvent typeEvent;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public String getTypeEventString() { return typeEvent.getName(); }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = TypeEvent.valueOf(typeEvent);
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

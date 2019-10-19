package ru.itmo.tpl.model;

public class Post {
    private long _id, user_id;
    private String title, text;

    public Post(long _id, long user_id, String title, String text) {
        this._id = _id;
        this.user_id = user_id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return _id;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

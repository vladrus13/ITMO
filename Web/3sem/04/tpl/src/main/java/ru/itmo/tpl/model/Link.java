package ru.itmo.tpl.model;

public class Link {
    private long id;
    private String link;
    private String name;

    public Link(long id, String link, String name) {
        this.id = id;
        this.link = link;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

}

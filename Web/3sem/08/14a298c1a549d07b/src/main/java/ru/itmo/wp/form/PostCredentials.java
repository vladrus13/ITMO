package ru.itmo.wp.form;

import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

public class PostCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @NotEmpty
    @Size(max = 1000)
    @Pattern(regexp = "([a-z]+[\\s]*)*", message = "Expected Latin letters and probel symbols")
    private String tages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTages() {
        return tages;
    }

    public void setTages(String tages) {
        this.tages = tages;
    }

    public Post toPost() {
        Post returned = new Post();
        returned.setText(this.getText());
        returned.setTitle(this.getTitle());
        Set<String> temp = new HashSet<String> (Arrays.asList(this.getTages().trim().split("\\s")));
        List<Tag> temp2 = new ArrayList<>();
        for (String it : temp) {
            Tag n = new Tag();
            n.setName(it);
            temp2.add(n);
        }
        returned.setTages(temp2);
        return returned;
    }
}

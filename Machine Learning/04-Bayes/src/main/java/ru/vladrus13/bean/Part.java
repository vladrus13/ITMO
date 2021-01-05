package ru.vladrus13.bean;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Part {
    private final ArrayList<Message> messages;

    public Part(Path path) {
        messages = new ArrayList<>();
        for (File file : Objects.requireNonNull(path.toFile().listFiles())) {
            messages.add(new Message(file.toPath()));
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}

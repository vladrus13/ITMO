package ru.vladrus13.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Message {
    private final int c;
    private final ArrayList<String> subject;
    private final ArrayList<String> message;

    public Message(Path path) {
        if (path.getFileName().toString().contains("spm")) {
            c = 0;
        } else {
            c = 1;
        }
        subject = new ArrayList<>();
        message = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String lineSubject = bufferedReader.readLine();
            String lineMessage = bufferedReader.readLine();
            lineMessage = bufferedReader.readLine();
            Arrays.stream(lineSubject.split(" ")).skip(1).forEach(subject::add);
            message.addAll(Arrays.asList(lineMessage.split(" ")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSubject() {
        return subject;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public ArrayList<String> getMessage(int n) {
        return new ArrayList<>(message.subList(0, n));
    }

    public ArrayList<ArrayList<String>> getMessages(int n) {
        ArrayList<ArrayList<String>> messages = new ArrayList<>();
        for (int i = 0; i < message.size() - n; i++) {
            messages.add(new ArrayList<>(message.subList(i, i + n)));
        }
        return messages;
    }

    public int getC() {
        return c;
    }
}

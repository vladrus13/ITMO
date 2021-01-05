package ru.vladrus13;

import ru.vladrus13.learning.LearningMachine;

import java.io.IOException;

/**
 * Launcher for learning machine
 */
public class LauncherLearning {

    /**
     * @param args ignored
     * @throws IOException if we have problem with files
     */
    public static void main(String[] args) throws IOException {
        LearningMachine.start();
    }
}

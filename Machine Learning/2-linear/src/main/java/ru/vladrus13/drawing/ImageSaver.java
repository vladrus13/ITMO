package ru.vladrus13.drawing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Image saver
 */
public class ImageSaver {
    /**
     * Save image
     *
     * @param image image
     * @param type  type of network
     */
    public static void save(BufferedImage image, String file, String type) {
        File imageFile = new File(String.valueOf(Path.of("resources")
                .resolve("images")
                .resolve(file)
                .resolve(type)
                .resolve("image.png")));
        if (!imageFile.getParentFile().exists()) {
            try {
                Files.createDirectories(Path.of(imageFile.getParentFile().getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ImageIO.write(image, "PNG", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

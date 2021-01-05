package ru.vladrus13.image;

import ru.vladrus13.kernel.KernelClass;

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
     * @param kernelClass type of network
     */
    public static void save(BufferedImage image, String file, KernelClass kernelClass) {
        File imageFile = new File(String.valueOf(Path.of("resources")
                .resolve("images")
                .resolve(file)
                .resolve(kernelClass.toFileString())
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

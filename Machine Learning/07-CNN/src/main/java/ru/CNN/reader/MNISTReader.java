package ru.CNN.reader;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.Tensor;
import ru.CNN.bean.Vector;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.lang.String.format;
import static ru.CNN.util.Utils.secondMatrix;
import static ru.CNN.util.Utils.secondVector;

public class MNISTReader {

    public static ArrayList<Tensor> readImages(Path path) {
        return getImages(path.toString());
    }

    public static ArrayList<Integer> readLabels(Path path) {
        return getLabels(path.toString());
    }

    public static final int LABEL_FILE_MAGIC_NUMBER = 2049;
    public static final int IMAGE_FILE_MAGIC_NUMBER = 2051;

    public static ArrayList<Integer> getLabels(String infile) {

        ByteBuffer bb = loadFileToByteBuffer(infile);

        assertMagicNumber(LABEL_FILE_MAGIC_NUMBER, bb.getInt());

        int numLabels = bb.getInt();
        ArrayList<Integer> labels = new ArrayList<>();

        for (int i = 0; i < numLabels; ++i)
            labels.add(bb.get() & 0xFF);

        return labels;
    }

    public static ArrayList<Tensor> getImages(String infile) {
        ByteBuffer bb = loadFileToByteBuffer(infile);

        assertMagicNumber(IMAGE_FILE_MAGIC_NUMBER, bb.getInt());

        int numImages = bb.getInt();
        int numRows = bb.getInt();
        int numColumns = bb.getInt();
        ArrayList<Tensor> tensors = new ArrayList<>();
        for (int i = 0; i < Math.min(100, numImages); i++) {
            tensors.add(readImage(numRows, numColumns, bb));
        }
        return tensors;
    }

    private static Tensor readImage(int numRows, int numCols, ByteBuffer bb) {
        Matrix matrix = Matrix.generate(numRows, numCols, 0.0);
        for (int row = 0; row < numRows; row++)
            matrix.apply(row, secondVector, readRow(numCols, bb));
        Tensor tensor = Tensor.generate(1, numRows, numCols, 0.0);
        tensor.apply(0, secondMatrix, matrix);
        return tensor;
    }

    private static Vector readRow(int numCols, ByteBuffer bb) {
        ArrayList<Double> row = new ArrayList<>();
        for (int col = 0; col < numCols; ++col)
            row.add((bb.get() & 0xFF) / 255.0);
        return new Vector(row);
    }

    public static void assertMagicNumber(int expectedMagicNumber, int magicNumber) {
        if (expectedMagicNumber != magicNumber) {
            switch (expectedMagicNumber) {
                case LABEL_FILE_MAGIC_NUMBER:
                    throw new RuntimeException("This is not a label file.");
                case IMAGE_FILE_MAGIC_NUMBER:
                    throw new RuntimeException("This is not an image file.");
                default:
                    throw new RuntimeException(
                            format("Expected magic number %d, found %d", expectedMagicNumber, magicNumber));
            }
        }
    }

    /*******
     * Just very ugly utilities below here. Best not to subject yourself to
     * them. ;-)
     ******/

    public static ByteBuffer loadFileToByteBuffer(String infile) {
        return ByteBuffer.wrap(loadFile(infile));
    }

    public static byte[] loadFile(String infile) {
        try {
            RandomAccessFile f = new RandomAccessFile(infile, "r");
            FileChannel chan = f.getChannel();
            long fileSize = chan.size();
            ByteBuffer bb = ByteBuffer.allocate((int) fileSize);
            chan.read(bb);
            bb.flip();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int i = 0; i < fileSize; i++)
                baos.write(bb.get());
            chan.close();
            f.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

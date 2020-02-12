package ru.ifmo.rain.kuznetsov.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardOpenOption.*;

public class Walk {

    private static int hash(int h, final byte[] bytes, int size) {
        for (int it = 0; it < size; it++) {
            h = (h * 0x01000193) ^ (bytes[it] & 0xff);
        }
        return h;
    }

    private static int hash(InputStream stream) throws IOException {
        int hash_sum = 0x811c9dc5;
        byte[] b = new byte[2048];
        int c;
        while ((c = stream.read(b)) >= 0) {
            hash_sum = hash(hash_sum, b, c);
        }
        return hash_sum;
    }

    private static void fileProcessing(Path path, BufferedWriter writer) {
        int hash_sum = 0;
        try {
            InputStream reader = Files.newInputStream(path, READ);
            hash_sum = hash(reader);
        } catch (IOException e) {
            // got smth wrong, but...
        }
        try {
            writer.write(String.format("%08x %s%n", hash_sum, path.toString()));
        } catch (IOException e) {
            System.err.println("ERROR: can't write to output file. Maybe: doesn't exist or not enough rights");
        }
    }

    private static void pathProcessing(String s, BufferedWriter writer) {
        try {
            Path start = Path.of(s);
            try {
                Files.walkFileTree(start, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                        fileProcessing(file, writer);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException e) {
                        try {
                            writer.write(String.format("%08x %s%n", 0, file.toString()));
                        } catch (IOException ex) {
                            System.err.println("ERROR: can't write to output file. Maybe: doesn't exist or not enough rights");
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.err.println("ERROR: WalkFileTree get some problems. Maybe: directory deleted while running");
            }
        } catch (NullPointerException | InvalidPathException e) {
            try {
                writer.write(String.format("%08x %s", 0, s));
            } catch (IOException ex) {
                System.err.println("ERROR: can't write to output file. Maybe: doesn't exist or not enough rights");
            }
        }

    }

    public static void main(String[] in) {
        if (in == null || in.length < 2) {
            System.err.println("ERROR: invalid count of input (not file)");
        } else {
            BufferedReader liner = null;
            try {
                liner = Files.newBufferedReader(Paths.get(in[0]), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("ERROR: can't read input file. Maybe: doesn't exist or not enough rights");
            } catch (NullPointerException e) {
                System.err.println("ERROR: where is first argument. Maybe: calling program with first null argument");
            } catch (InvalidPathException e) {
                System.err.println("ERROR: Invalid path input file. Maybe: mistake in path to input file");
            } catch (Exception e) {
                System.err.println("ERROR: An error was found in processing the first arg." + e.toString());
            }
            BufferedWriter writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get(in[1]), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("ERROR: can't read output file. Maybe: doesn't exist or not enough rights");
            } catch (NullPointerException e) {
                System.err.println("ERROR: where is second argument. Maybe: calling program with second null argument");
            } catch (InvalidPathException e) {
                System.err.println("ERROR: Invalid path output file. Maybe: mistake in path to output file");
            } catch (Exception e) {
                System.err.println("ERROR: An error was found in processing the second arg." + e.toString());
            }
            if (liner != null && writer != null) {
                String line;
                try {
                    while ((line = liner.readLine()) != null) {
                        pathProcessing(line, writer);
                    }
                } catch (IOException e) {
                    System.err.println("ERROR: can't read input file. Maybe: doesn't exist or not enough rights");
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("ERROR: can't close output file. Maybe: doesn't exist or not enough rights");
                }
            }
        }
    }
}

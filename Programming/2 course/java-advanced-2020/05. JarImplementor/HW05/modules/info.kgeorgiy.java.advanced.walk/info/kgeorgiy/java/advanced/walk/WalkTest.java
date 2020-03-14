package info.kgeorgiy.java.advanced.walk;

import info.kgeorgiy.java.advanced.base.BaseTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tests for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-walk">Walk</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalkTest extends BaseTest {
    protected static final Path DIR = Path.of("__Test__Walk__");
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    protected static final Random RANDOM = new Random(23084701432182342L);
    private String alphabet = ALPHABET;

    @Rule
    public TestName name = new TestName();

    private Path getTestDir() {
        return DIR.resolve(name.getMethodName());
    }

    @Test
    public void test10_oneEmptyFile() throws IOException {
        test(randomFiles(1, 0));
    }

    @Test
    public void test15_tenEmptyFiles() throws IOException {
        test(randomFiles(10, 0));
    }

    @Test
    public void test20_smallRandomFiles() throws IOException {
        test(randomFiles(10, 100));
    }

    @Test
    public void test21_mediumRandomFiles() throws IOException {
        test(randomFiles(10, 100));
    }

    @Test
    public void test22_largeRandomFiles() throws IOException {
        test(randomFiles(10, 1_000_000));
    }

    @Test
    public void test23_veryLargeFile() throws IOException {
        test(randomFiles(1, 10_000_000));
    }

    @Test
    public void test30_missingFiles() throws IOException {
        final Map<String, Integer> files = randomFiles(3, 0);
        files.put(randomFileName(), 0);
        files.put(randomFileName(), 0);
        files.put(randomFileName(), 0);
        test(files);
    }

    @Test
    public void test40_errorReading() throws IOException {
        final Map<String, Integer> files = randomFiles(3, 0);
        files.put(DIR.toString() + "..", 0);
        files.put(DIR.toString() + "@", 0);
        test(files);
    }

    @Test
    public void test50_whitespaceSupport() throws IOException {
        testAlphabet(10, 100, " \u00a0_");
    }

    @Test
    public void test55_chineseSupport() throws IOException {
        testAlphabet(10, 100, "\u8acb\u554f\u4f60\u7684\u7a0b\u5e8f\u652f\u6301\u4e2d\u570b");
    }

    private void testAlphabet(final int n, final int maxL, final String alphabet) throws IOException {
        this.alphabet = alphabet;
        test(randomFiles(n, maxL));
        this.alphabet = ALPHABET;
    }

    @Test
    public void test60_noInput() {
        runRaw(randomFileName(), randomFileName());
    }

    @Test
    public void test61_invalidInput() {
        runRaw("/", randomFileName());
        runRaw("\0*", randomFileName());
    }

    @Test
    public void test62_invalidOutput() throws IOException {
        final String input = createEmptyFile(name.getMethodName());
        runRaw(input, "/");
        runRaw(input, "\0*");
        final String file = createEmptyFile(name.getMethodName());
        runRaw(input, file + "/" + randomFileName());
    }

    @Test
    public void test63_invalidFiles() throws IOException {
        testAlphabet(1, 10, "\0\\*");
    }

    @Test
    public void test70_singleArgument() throws IOException {
        runRaw(createEmptyFile(name.getMethodName()));
    }

    @Test
    public void test71_noArguments() {
        runRaw();
    }

    @Test
    public void test72_nullArguments() {
        runRaw((String[]) null);
    }

    @Test
    public void test73_firstArgumentNull() {
        runRaw(null, "");
    }

    @Test
    public void test74_secondArgumentNull() throws IOException {
        runRaw(createEmptyFile(name.getMethodName()), null);
    }

    @Test
    public void test75_threeArguments() throws IOException {
        runRaw(createEmptyFile("a"), createEmptyFile("b"), "c");
    }

    @Test
    public void test80_partiallyMissingFiles() throws IOException {
        final Map<String, Integer> files = new LinkedHashMap<>();
        files.put("no-such-file-1", 0);
        files.putAll(randomFiles(10, 100));
        files.put("no-such-file-2", 0);
        files.putAll(randomFiles(10, 100));
        files.put("no-such-file-3", 0);
        test(files);
    }

    private Map<String, Integer> randomFiles(final int n, final int maxL) throws IOException {
        return randomFiles(n, maxL, getTestDir());
    }

    private static String createEmptyFile(final String name) throws IOException {
        final Path input = DIR.resolve(name);
        Files.write(input, new byte[0]);
        return input.toString();
    }

    protected void test(final Map<String, Integer> files) {
        test(files.keySet(), files);
    }

    protected void test(final Collection<String> inputs, final Map<String, Integer> files) {
        final Path inputFile = DIR.resolve(name.getMethodName() + ".in");
        final Path outputFile = DIR.resolve(name.getMethodName() + ".out");
        try {
            Files.writeString(inputFile, generateInput(inputs));
        } catch (final IOException e) {
            throw new AssertionError("Cannot write input file " + inputFile);
        }
        run(inputFile, outputFile);
        try {
            for (final String line : Files.readAllLines(outputFile, Charset.forName("UTF-8"))) {
                final String[] parts = line.split(" ", 2);
                Assert.assertEquals("Invalid line format\n" + line, 2, parts.length);
                Assert.assertTrue("Unexpected file " + parts[1], files.containsKey(parts[1]));
                Assert.assertEquals("Wrong hash", String.format("%08x", files.remove(parts[1])), parts[0]);
            }
        } catch (final IOException e) {
            throw new AssertionError("Cannot write output file " + outputFile);
        }

        Assert.assertTrue("Some files missing: \n    " + String.join("\n    ", files.keySet()), files.isEmpty());
    }

    private void run(final Path inputFile, final Path outputFile) {
        runRaw(inputFile.toString(), outputFile.toString());
    }

    private void runRaw(final String... args) {
        final Method method;
        final Class<?> cut = loadClass();
        try {
            method = cut.getMethod("main", String[].class);
        } catch (final NoSuchMethodException e) {
            throw new AssertionError("Cannot find method main(String[]) of " + cut, e);
        }
        System.out.println("Running " + name.getMethodName());
        try {
            method.invoke(null, (Object) args);
        } catch (final IllegalAccessException e) {
            throw new AssertionError("Cannot call main(String[]) of " + cut, e);
        } catch (final InvocationTargetException e) {
            throw new AssertionError("Error thrown", e.getCause());
        }
    }

    private static String generateInput(final Collection<String> files) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        files.forEach(writer::println);
        writer.close();
        return stringWriter.toString();
    }

    protected Map<String, Integer> randomFiles(final int n, final int maxL, final Path dir) throws IOException {
        Files.createDirectories(dir);
        final Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < n; i++) {
            final String name = randomFileName();
            try {
                final Path file = dir.resolve(name);
                final byte[] bytes = new byte[RANDOM.nextInt(maxL + 1)];
                RANDOM.nextBytes(bytes);
                Files.write(file, bytes);
                result.put(file.toString(), hash(bytes));
            } catch (final InvalidPathException ignore) {
                result.put(dir + "/" + name, 0);
            }
        }
        return result;
    }

    protected String randomFileName() {
        return RANDOM.ints(30, 0, alphabet.length())
                .mapToObj(i -> alphabet.substring(i, i + 1))
                .collect(Collectors.joining());
    }

    private static int hash(final byte[] bytes) {
        int h = 0x811c9dc5;
        for (final byte b : bytes) {
            h = (h * 0x01000193) ^ (b & 0xff);
        }
        return h;
    }
}

package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileMd2HtmlSource extends Md2HtmlSource {
    private final Reader reader;

    public FileMd2HtmlSource(final String fileName) throws Md2HtmlException {
        try {
            reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw error("Error opening input file '%s': %s", fileName, e.getMessage());
        }
    }

    @Override
    protected char readChar() throws IOException {
        final int read = reader.read();
        return read == -1 ? END : (char) read;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

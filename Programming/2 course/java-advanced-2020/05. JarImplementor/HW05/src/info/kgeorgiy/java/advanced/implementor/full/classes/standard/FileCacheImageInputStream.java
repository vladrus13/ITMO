package info.kgeorgiy.java.advanced.implementor.full.classes.standard;

import javax.imageio.stream.ImageInputStreamImpl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;

/**
 * Copy of {@link javax.imageio.stream.FileCacheImageInputStream}
 */
public class FileCacheImageInputStream extends ImageInputStreamImpl {

    private InputStream stream;

    private File cacheFile;

    private RandomAccessFile cache;

    private static final int BUFFER_LENGTH = 1024;

    private byte[] buf = new byte[BUFFER_LENGTH];

    private long length = 0L;

    private boolean foundEOF = false;

    /**
     * Constructs a {@code FileCacheImageInputStream} that will read
     * from a given {@code InputStream}.
     *
     * <p> A temporary file is used as a cache.  If
     * {@code cacheDir} is non-{@code null} and is a
     * directory, the file will be created there.  If it is
     * {@code null}, the system-dependent default temporary-file
     * directory will be used (see the documentation for
     * {@code File.createTempFile} for details).
     *
     * @param stream an {@code InputStream} to read from.
     * @param cacheDir a {@code File} indicating where the
     * cache file should be created, or {@code null} to use the
     * system directory.
     *
     * @exception IllegalArgumentException if {@code stream} is
     * {@code null}.
     * @exception IllegalArgumentException if {@code cacheDir} is
     * non-{@code null} but is not a directory.
     * @throws IOException if a cache file cannot be created.
     */
    public FileCacheImageInputStream(InputStream stream, File cacheDir)
            throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("stream == null!");
        }
        if ((cacheDir != null) && !(cacheDir.isDirectory())) {
            throw new IllegalArgumentException("Not a directory!");
        }
        this.stream = stream;
        if (cacheDir == null)
            this.cacheFile = Files.createTempFile("imageio", ".tmp").toFile();
        else
            this.cacheFile = Files.createTempFile(cacheDir.toPath(), "imageio", ".tmp")
                    .toFile();
        this.cache = new RandomAccessFile(cacheFile, "rw");
    }

    /**
     * Ensures that at least {@code pos} bytes are cached,
     * or the end of the source is reached.  The return value
     * is equal to the smaller of {@code pos} and the
     * length of the source file.
     *
     * @throws IOException if an I/O error occurs while reading from the
     * source file
     */
    private long readUntil(long pos) throws IOException {
        // We've already got enough data cached
        if (pos < length) {
            return pos;
        }
        // pos >= length but length isn't getting any bigger, so return it
        if (foundEOF) {
            return length;
        }

        long len = pos - length;
        cache.seek(length);
        while (len > 0) {
            // Copy a buffer's worth of data from the source to the cache
            // BUFFER_LENGTH will always fit into an int so this is safe
            int nbytes =
                    stream.read(buf, 0, (int)Math.min(len, (long)BUFFER_LENGTH));
            if (nbytes == -1) {
                foundEOF = true;
                return length;
            }

            cache.write(buf, 0, nbytes);
            len -= nbytes;
            length += nbytes;
        }

        return pos;
    }

    public int read() throws IOException {
        checkClosed();
        bitOffset = 0;
        long next = streamPos + 1;
        long pos = readUntil(next);
        if (pos >= next) {
            cache.seek(streamPos++);
            return cache.read();
        } else {
            return -1;
        }
    }

    public int read(byte[] b, int off, int len) throws IOException {
        checkClosed();

        if (b == null) {
            throw new NullPointerException("b == null!");
        }
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                    ("off < 0 || len < 0 || off+len > b.length || off+len < 0!");
        }

        bitOffset = 0;

        if (len == 0) {
            return 0;
        }

        long pos = readUntil(streamPos + len);

        // len will always fit into an int so this is safe
        len = (int)Math.min((long)len, pos - streamPos);
        if (len > 0) {
            cache.seek(streamPos);
            cache.readFully(b, off, len);
            streamPos += len;
            return len;
        } else {
            return -1;
        }
    }

    /**
     * Returns {@code true} since this
     * {@code ImageInputStream} caches data in order to allow
     * seeking backwards.
     *
     * @return {@code true}.
     *
     * @see #isCachedMemory
     * @see #isCachedFile
     */
    public boolean isCached() {
        return true;
    }

    /**
     * Returns {@code true} since this
     * {@code ImageInputStream} maintains a file cache.
     *
     * @return {@code true}.
     *
     * @see #isCached
     * @see #isCachedMemory
     */
    public boolean isCachedFile() {
        return true;
    }

    /**
     * Returns {@code false} since this
     * {@code ImageInputStream} does not maintain a main memory
     * cache.
     *
     * @return {@code false}.
     *
     * @see #isCached
     * @see #isCachedFile
     */
    public boolean isCachedMemory() {
        return false;
    }

    /**
     * Closes this {@code FileCacheImageInputStream}, closing
     * and removing the cache file.  The source {@code InputStream}
     * is not closed.
     *
     * @throws IOException if an error occurs.
     */
    public void close() throws IOException {
        super.close();
        stream = null;
        cache = null;
        cacheFile = null;
    }

}

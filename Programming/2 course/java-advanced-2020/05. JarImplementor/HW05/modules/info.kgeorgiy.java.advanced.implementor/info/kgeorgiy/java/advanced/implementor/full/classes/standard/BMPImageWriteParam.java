package info.kgeorgiy.java.advanced.implementor.full.classes.standard;

import javax.imageio.ImageWriteParam;
import java.util.Locale;

/**
 * Copy of {@link javax.imageio.plugins.bmp.BMPImageWriteParam}
 */
public class BMPImageWriteParam extends ImageWriteParam {

    private boolean topDown = false;

    /**
     * Constructs a {@code BMPImageWriteParam} set to use a given
     * {@code Locale} and with default values for all parameters.
     *
     * @param locale a {@code Locale} to be used to localize
     * compression type names and quality descriptions, or
     * {@code null}.
     */
    public BMPImageWriteParam(Locale locale) {
        super(locale);

        // Set compression types ("BI_RGB" denotes uncompressed).
        // Set compression flag.
        canWriteCompressed = true;
        compressionMode = MODE_COPY_FROM_METADATA;
    }

    /**
     * Constructs an {@code BMPImageWriteParam} object with default
     * values for all parameters and a {@code null Locale}.
     */
    public BMPImageWriteParam() {
        this(null);
    }

    /**
     * If set, the data will be written out in a top-down manner, the first
     * scanline being written first.
     *
     * @param topDown whether the data are written in top-down order.
     */
    public void setTopDown(boolean topDown) {
        this.topDown = topDown;
    }

    /**
     * Returns the value of the {@code topDown} parameter.
     * The default is {@code false}.
     *
     * @return whether the data are written in top-down order.
     */
    public boolean isTopDown() {
        return topDown;
    }
}

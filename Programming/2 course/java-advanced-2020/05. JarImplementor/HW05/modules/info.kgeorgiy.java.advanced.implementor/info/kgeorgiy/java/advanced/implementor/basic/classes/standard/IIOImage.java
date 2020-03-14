package info.kgeorgiy.java.advanced.implementor.basic.classes.standard;

import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.util.List;

/**
 * Copy of {@link javax.imageio.IIOImage}
 */
public class IIOImage {

    /**
     * The {@code RenderedImage} being referenced.
     */
    protected RenderedImage image;

    /**
     * The {@code Raster} being referenced.
     */
    protected Raster raster;

    /**
     * A {@code List} of {@code BufferedImage} thumbnails,
     * or {@code null}.  Non-{@code BufferedImage} objects
     * must not be stored in this {@code List}.
     */
    protected List<? extends BufferedImage> thumbnails = null;

    /**
     * An {@code IIOMetadata} object containing metadata
     * associated with the image.
     */
    protected IIOMetadata metadata;

    /**
     * Constructs an {@code IIOImage} containing a
     * {@code RenderedImage}, and thumbnails and metadata
     * associated with it.
     *
     * <p> All parameters are stored by reference.
     *
     * <p> The {@code thumbnails} argument must either be
     * {@code null} or contain only {@code BufferedImage}
     * objects.
     *
     * @param image a {@code RenderedImage}.
     * @param thumbnails a {@code List} of {@code BufferedImage}s,
     * or {@code null}.
     * @param metadata an {@code IIOMetadata} object, or
     * {@code null}.
     *
     * @exception IllegalArgumentException if {@code image} is
     * {@code null}.
     */
    public IIOImage(RenderedImage image,
                    List<? extends BufferedImage> thumbnails,
                    IIOMetadata metadata) {
        if (image == null) {
            throw new IllegalArgumentException("image == null!");
        }
        this.image = image;
        this.raster = null;
        this.thumbnails = thumbnails;
        this.metadata = metadata;
    }

    /**
     * Constructs an {@code IIOImage} containing a
     * {@code Raster}, and thumbnails and metadata
     * associated with it.
     *
     * <p> All parameters are stored by reference.
     *
     * @param raster a {@code Raster}.
     * @param thumbnails a {@code List} of {@code BufferedImage}s,
     * or {@code null}.
     * @param metadata an {@code IIOMetadata} object, or
     * {@code null}.
     *
     * @exception IllegalArgumentException if {@code raster} is
     * {@code null}.
     */
    public IIOImage(Raster raster,
                    List<? extends BufferedImage> thumbnails,
                    IIOMetadata metadata) {
        if (raster == null) {
            throw new IllegalArgumentException("raster == null!");
        }
        this.raster = raster;
        this.image = null;
        this.thumbnails = thumbnails;
        this.metadata = metadata;
    }

    /**
     * Returns the currently set {@code RenderedImage}, or
     * {@code null} if only a {@code Raster} is available.
     *
     * @return a {@code RenderedImage}, or {@code null}.
     *
     * @see #setRenderedImage
     */
    public RenderedImage getRenderedImage() {
        synchronized(this) {
            return image;
        }
    }

    /**
     * Sets the current {@code RenderedImage}.  The value is
     * stored by reference.  Any existing {@code Raster} is
     * discarded.
     *
     * @param image a {@code RenderedImage}.
     *
     * @exception IllegalArgumentException if {@code image} is
     * {@code null}.
     *
     * @see #getRenderedImage
     */
    public void setRenderedImage(RenderedImage image) {
        synchronized(this) {
            if (image == null) {
                throw new IllegalArgumentException("image == null!");
            }
            this.image = image;
            this.raster = null;
        }
    }

    /**
     * Returns {@code true} if this {@code IIOImage} stores
     * a {@code Raster} rather than a {@code RenderedImage}.
     *
     * @return {@code true} if a {@code Raster} is
     * available.
     */
    public boolean hasRaster() {
        synchronized(this) {
            return (raster != null);
        }
    }

    /**
     * Returns the currently set {@code Raster}, or
     * {@code null} if only a {@code RenderedImage} is
     * available.
     *
     * @return a {@code Raster}, or {@code null}.
     *
     * @see #setRaster
     */
    public Raster getRaster() {
        synchronized(this) {
            return raster;
        }
    }

    /**
     * Sets the current {@code Raster}.  The value is
     * stored by reference.  Any existing {@code RenderedImage} is
     * discarded.
     *
     * @param raster a {@code Raster}.
     *
     * @exception IllegalArgumentException if {@code raster} is
     * {@code null}.
     *
     * @see #getRaster
     */
    public void setRaster(Raster raster) {
        synchronized(this) {
            if (raster == null) {
                throw new IllegalArgumentException("raster == null!");
            }
            this.raster = raster;
            this.image = null;
        }
    }

    /**
     * Returns the number of thumbnails stored in this
     * {@code IIOImage}.
     *
     * @return the number of thumbnails, as an {@code int}.
     */
    public int getNumThumbnails() {
        return thumbnails == null ? 0 : thumbnails.size();
    }

    /**
     * Returns a thumbnail associated with the main image.
     *
     * @param index the index of the desired thumbnail image.
     *
     * @return a thumbnail image, as a {@code BufferedImage}.
     *
     * @exception IndexOutOfBoundsException if the supplied index is
     * negative or larger than the largest valid index.
     * @exception ClassCastException if a
     * non-{@code BufferedImage} object is encountered in the
     * list of thumbnails at the given index.
     *
     * @see #getThumbnails
     * @see #setThumbnails
     */
    public BufferedImage getThumbnail(int index) {
        if (thumbnails == null) {
            throw new IndexOutOfBoundsException("No thumbnails available!");
        }
        return (BufferedImage)thumbnails.get(index);
    }

    /**
     * Returns the current {@code List} of thumbnail
     * {@code BufferedImage}s, or {@code null} if none is
     * set.  A live reference is returned.
     *
     * @return the current {@code List} of
     * {@code BufferedImage} thumbnails, or {@code null}.
     *
     * @see #getThumbnail(int)
     * @see #setThumbnails
     */
    public List<? extends BufferedImage> getThumbnails() {
        return thumbnails;
    }

    /**
     * Sets the list of thumbnails to a new {@code List} of
     * {@code BufferedImage}s, or to {@code null}.  The
     * reference to the previous {@code List} is discarded.
     *
     * <p> The {@code thumbnails} argument must either be
     * {@code null} or contain only {@code BufferedImage}
     * objects.
     *
     * @param thumbnails a {@code List} of
     * {@code BufferedImage} thumbnails, or {@code null}.
     *
     * @see #getThumbnail(int)
     * @see #getThumbnails
     */
    public void setThumbnails(List<? extends BufferedImage> thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     * Returns a reference to the current {@code IIOMetadata}
     * object, or {@code null} is none is set.
     *
     * @return an {@code IIOMetadata} object, or {@code null}.
     *
     * @see #setMetadata
     */
    public IIOMetadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the {@code IIOMetadata} to a new object, or
     * {@code null}.
     *
     * @param metadata an {@code IIOMetadata} object, or
     * {@code null}.
     *
     * @see #getMetadata
     */
    public void setMetadata(IIOMetadata metadata) {
        this.metadata = metadata;
    }
}

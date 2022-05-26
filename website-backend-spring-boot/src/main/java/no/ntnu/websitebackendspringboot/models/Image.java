package no.ntnu.websitebackendspringboot.models;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Model/Entity for storing image data
 */
@Entity(name = "Image")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "Image_Id")
    private Integer id;
    @Lob
    private byte[] data;
    private String extension;
    private String contentType;

    // Specifies the IMAGE table does not contain an owner column, but
    // an PRODUCT_ID column with a foreign key. And creates a join to
    // lazily fetch the owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = true)
    private Product product;

    public Image() {
    }

    public Image(byte[] data, String extension, String contentType) {
        this.data = data;
        this.extension = extension;
        this.contentType = contentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return id + "." + extension;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", data=" + Arrays.toString(data) +
                ", extension='" + extension + '\'' +
                ", contentType='" + contentType + '\'' +
                ", product=" + product +
                '}';
    }
}

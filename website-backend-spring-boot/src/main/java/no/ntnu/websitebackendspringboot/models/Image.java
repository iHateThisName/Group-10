package no.ntnu.websitebackendspringboot.models;

import javax.persistence.*;

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
}

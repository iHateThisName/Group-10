package no.ntnu.websitebackendspringboot.entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Model/Entity for storing image data
 */
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "imageId")
    private Integer imageId;
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

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer id) {
        this.imageId = id;
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
        return imageId + "." + extension;
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
                "imageId=" + imageId +
                ", data=" + Arrays.toString(data) +
                ", extension='" + extension + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}

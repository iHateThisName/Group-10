package no.ntnu.websitebackendspringboot.models;

import javax.persistence.*;
import java.util.List;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 *
 * This is a model/entity in the database.
 */
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    @Column(name = "Product_name")
    private String name;
    @Column(name = "Product_Description")
    private String description;
    @Column(name = "Price")
    private double price;
    @Column(name = "Image_Id")
    private int imageId;

    // The 'mappedBy = "product"' attribute specifies that
    // the 'private Product product;' field in Image owns the
    // relationship (i.e. contains the foreign key for the query to
    // find all images for a product.)
    @OneToMany(mappedBy = "product")
    private List<Image> images;


    /**
     * The JPA need an empty constructor to operate
     */
    public Product() {
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageId=" + imageId +
                ", images=" + images +
                '}';
    }
}
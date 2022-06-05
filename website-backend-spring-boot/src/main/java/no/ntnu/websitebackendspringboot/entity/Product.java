package no.ntnu.websitebackendspringboot.entity;

import no.ntnu.websitebackendspringboot.services.ProductService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 *
 * This is a model/entity in the database.
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int id;

    @Column(name = "productName")
    private String name;

    @Column(name = "productDescription")
    private String description;

    @Column(name = "productPrice")
    private double price;

    @Column(name = "productAmount")
    private int productAmount;

    @Column(name = "category")
    @ElementCollection //The @ElementCollection is used to map non-entities
    private List<String> categories;

    @OneToMany(targetEntity = Image.class, cascade = CascadeType.ALL)
    @JoinTable(name = "productImages",
            joinColumns = @JoinColumn(name = "productId", referencedColumnName = "productId"),
            inverseJoinColumns = @JoinColumn(name = "imageId", referencedColumnName = "imageId"))
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
        this.categories = new ArrayList<>();
        this.images = new ArrayList<>();
        categories.add("All");

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

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> category) {
        this.categories = category;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Adds an image to the product.
     *
     * @param image the image to be added.
     */
    public void addImage(Image image) {

        //Check that the image does not already exist
        if (!images.contains(image)) {
            images.add(image);
        }
    }

    public void addCategory(String categoryName) {
        final String[] everyCategory = {"All", "Men", "Women", "Dog"};

        //Check if the category already exist
        if (!categories.contains(categoryName)) {
            for (String category : everyCategory) {
                if (categoryName.equalsIgnoreCase(category)) {
                    categories.add(category);
                }
            }
        }
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", images=" + images +
                '}';
    }
}

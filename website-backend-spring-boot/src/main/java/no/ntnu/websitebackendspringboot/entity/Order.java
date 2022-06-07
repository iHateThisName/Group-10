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
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int id;



    /**
     * The JPA need an empty constructor to operate
     */
    public Order() {
    }

    public Order(int id, String name, String category, double price) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

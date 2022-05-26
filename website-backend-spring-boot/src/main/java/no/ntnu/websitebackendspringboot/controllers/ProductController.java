package no.ntnu.websitebackendspringboot.controllers;

import no.ntnu.websitebackendspringboot.models.Product;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A REST API for products
 */
@Controller
@RequestMapping(path = "/api")
public class ProductController {

    private ProductService productService;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products stored in the database
     *
     * @return List of all products
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() {
        try {
            Thread.sleep(2000); // Imitate long loading of data...
        } catch (InterruptedException e) {

            log.error("Product-loading thread interrupted!");
        }
        return ResponseEntity.ok().body(productService.getAll());
    }

    /**
     * Add a new product to the database. Called on HTTP POST.
     *
     * @param product Product to add
     * @return HTTP OK on success and product ID in the body, BAD REQUEST on error
     */
    @PostMapping("/products")
    public ResponseEntity<String> add(@RequestBody Product product) {
        ResponseEntity<String> response;
        String errorMessage = productService.add(product);
        if (errorMessage == null) {
            response = new ResponseEntity<>("" + product.getId(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Update a product int the database. Called on HTTP PUT.
     *
     * @param id      ID of the product to update
     * @param product Product data
     * @return HTTP OK on success, BAD REQUEST on error
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<String> update(@RequestBody Product product, @PathVariable Integer id) {
        ResponseEntity<String> response;
        String errorMessage = productService.update(id, product);
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Delete a product from the database. Called on HTTP DELETE.
     *
     * @param id ID of the product to delete
     * @return HTTP OK on success, BAD REQUEST on error
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        ResponseEntity<String> response;
        String errorMessage = productService.delete(id);
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}

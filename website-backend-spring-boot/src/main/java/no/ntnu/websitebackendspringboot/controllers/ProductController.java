package no.ntnu.websitebackendspringboot.controllers;

import no.ntnu.websitebackendspringboot.entity.Product;
import no.ntnu.websitebackendspringboot.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A REST API for products
 */
@RestController
@RequestMapping(path = "/api")
public class ProductController {

    private ProductService productService;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products stored in the database. Called on HTTP GET.
     *
     * @return List of all products
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() {

        productService.getAll().forEach(product -> log.info("image id " + product.toString()));
        return ResponseEntity.ok().body(productService.getAll());
    }

    /**
     * Add a new product to the database. Called on HTTP POST.
     *
     * @param product Product to add
     * @return HTTP OK on success and product ID in the body, BAD REQUEST on error
     */
    @PostMapping("/products")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<String> add(@RequestBody Product product) {
        ResponseEntity<String> response;
        String errorMessage = productService.add(product);
        if (errorMessage == null) {
            response = new ResponseEntity<>("" + product.toString(), HttpStatus.OK);
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
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
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
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
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

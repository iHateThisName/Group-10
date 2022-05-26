package no.ntnu.websitebackendspringboot.services;

import no.ntnu.websitebackendspringboot.models.Product;
import no.ntnu.websitebackendspringboot.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for products
 */
@Service
@Transactional
public class ProductServiceImplementation implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new LinkedList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    /**
     * Get one product by it's ID
     *
     * @param id Unique ID
     * @return The product or null if none found by that ID
     */
    @Override
    public Product getById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * Update a product in the database
     *
     * @param id      The ID of the product to update
     * @param product the new data for the product
     * @return null on success, error message on error
     */
    @Override
    public String update(Integer id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        String errorMessage = null;
        if (existingProduct.isPresent()) {
            product.setId(id);
            try {
                productRepository.save(product);
            } catch (Exception e) {
                errorMessage = "Error while saving the product to DB: " + e.getMessage();
            }
        } else {
            errorMessage = "Product with given ID not found";
        }
        return errorMessage;
    }

    /**
     * Delete a product from the database
     * @param id ID of the product to delete
     * @return null on success, error message on error
     */
    @Override
    public String delete(Integer id) {
        String errorMessage = null;
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            errorMessage = "Error while deleting a product from DB: " + e.getMessage();
        }
        return errorMessage;
    }

    /**
     * Add a new product to the database
     * @param product
     * @return null on success, error message on error
     */
    @Override
    public String add(Product product) {
        String errorMessage = null;
        if (product.getId() > 0) {
            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if (existingProduct.isPresent()) {
                errorMessage = "Product with that ID already exists";
            }
        }
        if (errorMessage == null) {
            try {
                Product savedProduct = productRepository.save(product);
                product.setId(savedProduct.getId());
            } catch (Exception e) {
                errorMessage = "Error while adding the product to DB: " + e.getMessage();
            }
        }
        return errorMessage;
    }
}
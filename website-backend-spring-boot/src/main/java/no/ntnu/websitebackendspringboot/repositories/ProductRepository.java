package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for sending queries and statements to product table in the SQL database
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByNameIgnoreCase(String name);
}

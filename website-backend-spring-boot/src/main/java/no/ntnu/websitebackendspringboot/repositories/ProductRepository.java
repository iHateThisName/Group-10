package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for sending queries and statements to product table in the SQL database
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByNameIgnoreCase(String name);
}

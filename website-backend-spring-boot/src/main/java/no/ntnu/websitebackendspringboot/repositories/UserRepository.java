package no.ntnu.websitebackendspringboot.repositories;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}

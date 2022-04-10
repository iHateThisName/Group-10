package no.ntnu.websitebackendspringboot.repositories;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}

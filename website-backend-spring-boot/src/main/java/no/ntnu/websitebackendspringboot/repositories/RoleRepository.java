package no.ntnu.websitebackendspringboot.repositories;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}

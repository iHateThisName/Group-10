package no.ntnu.websitebackendspringboot.repositories;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}

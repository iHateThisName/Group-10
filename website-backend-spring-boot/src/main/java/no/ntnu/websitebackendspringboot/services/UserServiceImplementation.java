package no.ntnu.websitebackendspringboot.services;

import java.util.List;
import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.repositories.RoleRepository;
import no.ntnu.websitebackendspringboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
// @Service tells spring that this is a service class.
@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  public UserServiceImplementation(
      UserRepository userRepository,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {

      return new AccessUserDetails(user.get());

    } else {
      throw new UsernameNotFoundException(String.format("User: %s not found in the database", username));
    }
  }

  @Override
  public User saveUser(User user) {
    log.info("Saving new user {} to the database", user.getName());
    return userRepository.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {} to the database", role.getName());
    return roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {

    //making it uppercase
    roleName = roleName.toUpperCase();
    //This string needs to be included
    String stringRole = "ROLE_";

    //Cheeks if the new role name has "ROLE_" in front of it
    if (!roleName.startsWith(stringRole)) {
      //if not that just add it
      roleName = stringRole + roleName;
    }

    log.info("Adding role {} to user {}", roleName, username);

    //I need the user to add the new role to the user
    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Username " + username + " not found"));

    //Now it needs to find the class Role to add to user
    Optional<Role> role = roleRepository.findByName(roleName);

    //finally, add the role to the user
    role.ifPresent(value -> user.getRoles().add(value));


  }

  @Override
  public User getUser(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isPresent()) {
      return userOptional.get();
    } else {
      log.warn("Username: {} was not found in the database", username);
      return null;
    }
  }

  @Override
  public List<User> getUsers() {

    log.info("Fetching all users");
    return userRepository.findAll();
  }
}

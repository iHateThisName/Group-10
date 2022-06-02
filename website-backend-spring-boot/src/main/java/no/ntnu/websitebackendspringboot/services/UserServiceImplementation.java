package no.ntnu.websitebackendspringboot.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import no.ntnu.websitebackendspringboot.entity.Role;
import no.ntnu.websitebackendspringboot.entity.User;
import no.ntnu.websitebackendspringboot.repositories.RoleRepository;
import no.ntnu.websitebackendspringboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
// @Service tells spring that this is a service class.
// @Transactional tells spring that everything in this class is transactional.
@Service
@Transactional
public class UserServiceImplementation implements UserService, UserDetailsService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  public UserServiceImplementation(UserRepository userRepository,
      RoleRepository roleRepository) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isEmpty()) {
      //user is empty
      log.error("User not found in the database");
    }
    if (userOptional.isPresent()) {
      //user is present
      log.info("User found in the database: {}", username);

      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      userOptional.get().getRoles().forEach(role ->
          authorities.add(new SimpleGrantedAuthority(role.getName())));

      //Return a user of type UserDetails and not this project User class.
      return new org.springframework.security.core.userdetails.User(
          userOptional.get().getUsername(), userOptional.get().getPassword(), authorities);

    }
    return null;
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
    Optional<Role> optionalRole = roleRepository.findByName(roleName);
    //adding the role
    optionalRole.ifPresent(value -> user.getRoles().add(value));
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

  @Override
  public List<Role> getRoles() {
    log.info("Fetching all roles");
    return roleRepository.findAll();
  }
}

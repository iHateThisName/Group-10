package no.ntnu.websitebackendspringboot;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.repositories.RoleRepository;
import no.ntnu.websitebackendspringboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  @Value("${DummyDataInitializer.user.password}")
  String password;

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public DummyDataInitializer(UserRepository userRepository,
                              RoleRepository roleRepository) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }


  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    Optional<User> arnold = userRepository.findByUsername("arnold");

    if (arnold.isEmpty()) {
      log.info("Adding users to database...");
      addData(password);
      log.info("Done adding users to database");
      StringBuilder stringBuilder = new StringBuilder();
      userRepository.findAll().forEach(user -> {
        stringBuilder
            .append("\n----------------------------\n")
            .append(user.getUsername())
            .append("\n")
            .append(user);
      });
      log.info("information about all the users: {}", stringBuilder);

    } else {
      log.info("Users already exist in the database");
    }

  }

  private void addData(String password) {

    Role user = new Role("user");
    Role manager = new Role("manager");
    Role admin = new Role("admin");
    roleRepository.save(user);
    roleRepository.save(manager);
    roleRepository.save(admin);


    User john = new User("john", password, "John Travolta");
    User will = new User("will", password, "Will Smith");
    User jim = new User("jim", password, "Jim Carry");
    User arnold = new User("arnold", password, "Arnold Schwarzenegger");

    john.addRole(user);
    will.addRole(manager);
    jim.addRole(admin);
    arnold.addRole(user);
    arnold.addRole(manager);
    arnold.addRole(admin);

    userRepository.save(john);
    userRepository.save(will);
    userRepository.save(jim);
    userRepository.save(arnold);

  }

}

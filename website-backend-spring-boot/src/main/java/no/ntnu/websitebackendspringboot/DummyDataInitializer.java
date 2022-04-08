package no.ntnu.websitebackendspringboot;

import java.util.Optional;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  private final UserService userService;

  public DummyDataInitializer(UserService userService) {
    this.userService = userService;
  }


  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    User arnold = userService.getUser("arnold");

    if (arnold == null) {
      logger.info("Adding users to database...");
      addData();
      logger.info("Done adding users to database");
    } else {
      logger.info("Users already exist in the database");
    }

  }

  private void addData() {

    userService.saveRole(new Role("user"));
    userService.saveRole(new Role("manager"));
    userService.saveRole(new Role("admin"));

    //todo the password should be encoded
    userService.saveUser(new User("john", "1234", "John Travolta"));
    userService.saveUser(new User("will", "1234", "Will Smith"));
    userService.saveUser(new User("jim", "1234", "Jim Carry"));
    userService.saveUser(new User("arnold", "1234", "Arnold Schwarzenegger"));

    userService.addRoleToUser("john", "user");
    userService.addRoleToUser("john", "manager");
    userService.addRoleToUser("will", "manager");
    userService.addRoleToUser("jim", "admin");
    userService.addRoleToUser("arnold", "admin");
    userService.addRoleToUser("arnold", "user");

  }

}

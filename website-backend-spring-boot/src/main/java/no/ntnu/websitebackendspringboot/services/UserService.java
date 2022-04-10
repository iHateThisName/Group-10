package no.ntnu.websitebackendspringboot.services;

import java.util.List;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
public interface UserService {

  //methode used to save the user
  User saveUser(User user);
  //to save a role
  Role saveRole(Role role);
  //add a role to a user
  void addRoleToUser(String username, String roleName);
  //retrieve a users by username
  User getUser(String username);
  //get a list of all the users
  List<User> getUsers();
}

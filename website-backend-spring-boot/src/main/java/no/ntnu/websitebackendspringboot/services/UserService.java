package no.ntnu.websitebackendspringboot.services;

import java.util.List;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);
    List<User> getUsers();
}

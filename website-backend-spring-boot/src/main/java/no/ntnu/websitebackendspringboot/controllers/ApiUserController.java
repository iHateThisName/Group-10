package no.ntnu.websitebackendspringboot.controllers;

import java.net.URI;
import java.util.List;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@RestController
@RequestMapping(path = "/api")
public class ApiUserController {

  private UserService userService;

  public ApiUserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    //Creating a responseEntity witch is what the browser receives
    //the .ok tells the user it is code 200
    //all the is place in the body
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @GetMapping("/roles")
  @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
  public ResponseEntity<List<Role>> getRoles() {
    return ResponseEntity.ok().body(userService.getRoles());
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> getAdmin() {
    return ResponseEntity.ok().body("YOU ARE A ADMIN");
  }

  @PostMapping("/user/save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    //.created will response with 201
    //Which means a resource was created in the server.
    URI uri = URI.create(
        ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/user/save").toUriString());
    return ResponseEntity.created(uri).body(userService.saveUser(user));
  }

  @PostMapping("/role/save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    //.created will response with 201
    //Which means a resource was created in the server.
    URI uri = URI.create(
        ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/role/save").toUriString());

    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }

  @PostMapping("/role/addRoleToUser")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {

    userService.addRoleToUser(form.getUsername(), form.getRoleName());
    return ResponseEntity.ok().build();
  }

  static
  class RoleToUserForm {
    private String username;
    private String roleName;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getRoleName() {
      return roleName;
    }

    public void setRoleName(String roleName) {
      this.roleName = roleName;
    }
  }
}

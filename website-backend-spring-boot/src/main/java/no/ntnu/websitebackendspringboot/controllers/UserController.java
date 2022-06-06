package no.ntnu.websitebackendspringboot.controllers;

import java.net.URI;
import java.util.List;
import no.ntnu.websitebackendspringboot.entity.Role;
import no.ntnu.websitebackendspringboot.entity.User;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@RestController
@RequestMapping(path = "/api")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }
//Todo /user and /role should be there own classes

  @GetMapping("/users")
  @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    //Creating a responseEntity witch is what the browser receives
    //the .ok tells the user it is code 200
    //all the is place in the body
    return ResponseEntity.ok().body(userService.getUsers());
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

  @GetMapping("/user/whoami")
  @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
  public ResponseEntity<User> getCurrentUser(@CookieValue(value = "access_Token") String token) {

    return ResponseEntity.ok().body(userService.getUserByToken(token));
  }

}

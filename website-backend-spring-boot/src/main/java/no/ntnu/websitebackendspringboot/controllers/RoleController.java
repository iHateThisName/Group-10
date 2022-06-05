package no.ntnu.websitebackendspringboot.controllers;

import no.ntnu.websitebackendspringboot.entity.Role;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/api")
public class RoleController {

    private UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
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


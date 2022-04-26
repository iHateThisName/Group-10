package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Controller
public class ProfileController {

  //Only access if you are authenticated
  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/profile")
  public String getProfile() {
    return "profile";
  }

}

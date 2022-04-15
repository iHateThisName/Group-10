package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Controller
public class ProfileController {

  //Only access if you are authenticated
  @RequestMapping(value = "/profile")
  public String getProfile() {
    return "profile";
  }

}

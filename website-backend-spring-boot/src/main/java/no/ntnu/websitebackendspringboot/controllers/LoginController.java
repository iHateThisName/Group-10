package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
@Controller
public class LoginController {

  @GetMapping(value = "/login")
  public String getLogin() {
    return "login";
  }
}

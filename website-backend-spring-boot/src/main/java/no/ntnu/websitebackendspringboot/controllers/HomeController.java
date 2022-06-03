package no.ntnu.websitebackendspringboot.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Controller
public class HomeController {

  /**
   * This methode handles request mapping that holds no value,
   * and redirect the user to /home.
   *
   * @return
   */
  @RequestMapping(value = "/")
  @PreAuthorize("permitAll()")
  public ModelAndView redirectToHomePage() {
    return new ModelAndView("redirect:" + "/home");
  }

  @RequestMapping(value = "/home")
  @PreAuthorize("permitAll()")
  public String getHome() {
    return "home";
  }

  //todo make own class
  @RequestMapping("/about")
  @PreAuthorize("permitAll()")
  public String getAbout() {
    return "about";
  }

  //todo make own class
  @RequestMapping("/store")
  @PreAuthorize("permitAll()")
  public String getStore() {
    return "store";
  }

  //todo make own class
  @GetMapping(value = "/faq")
  public  String getFaq() {
    return "faq";
  }

  //todo make own class
  @GetMapping(value = "/gallery")
  @PreAuthorize("permitAll()")
  public  String getGallery() {
    return "gallery";
  }

  @GetMapping(path = "/example")
  @PreAuthorize("permitAll()")
  public String example(Model model) {
    String msg = "This is using a String variable from the backend";
    model.addAttribute("message", msg);
    return "thymeleaf-example";
  }

  //todo make own class
  @GetMapping(value = "/api/Header")
  @PreAuthorize("permitAll()")
  public String getHeader() {
    return "Header";
  }

  //todo make own class
  @GetMapping(value = "/api/Footer")
  @PreAuthorize("permitAll()")
  public String getFooter() {
    return "Footer";
  }

}

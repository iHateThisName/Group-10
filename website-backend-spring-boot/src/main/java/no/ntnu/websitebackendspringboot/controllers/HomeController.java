package no.ntnu.websitebackendspringboot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public ModelAndView redirectToHomePage() {
    return new ModelAndView("redirect:" + "/home");
  }

  @RequestMapping(value = "/home")
  public String getHome() {
    return "home";
  }


  @RequestMapping("/about")
  public String getAbout() {
    return "about";
  }

  @RequestMapping("/store")
  public String getStore() {
    return "store";
  }

  @GetMapping(value = "/faq")
  public  String getFaq() {
    return "faq";
  }

  @GetMapping(path = "/example")
  public String example(Model model) {
    String msg = "This is using a String variable from the backend";
    model.addAttribute("message", msg);
    return "thymeleaf-example";
  }


}

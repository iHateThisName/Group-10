package no.ntnu.websitebackendspringboot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHome() {
        return "home";
    }

    //TODO a controller for about
    @RequestMapping("/about")
    public String getAbout() {
        return "about";
    }

    //TODO a controller for store
    @RequestMapping("/store")
    public String getStore() {
        return "store";
    }

    @GetMapping(path = "/example")
    public String example(Model model) {
        String msg = "This is using a String variable from the backend";
        model.addAttribute("message", msg);
        return "thymeleaf-example";
    }


}

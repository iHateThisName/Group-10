package no.ntnu.websitebackendspringboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping(path = "/example")
    public String example(Model model) {
        String msg = "This is using a String variable from the backend";
        model.addAttribute("message", msg);
        return "example Thymeleaf";
    }


}

package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

    @GetMapping(value = "/api/footer")
    public String getFooter() {
        return "Footer";
    }
}

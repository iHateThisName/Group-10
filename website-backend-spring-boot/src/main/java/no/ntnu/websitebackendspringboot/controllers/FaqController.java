package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping(value = "/faq")
    public String getFaq() {
        return "Faq";
    }
}











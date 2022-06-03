package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {
    @GetMapping(value = "/api/Footer")
    @PreAuthorize("permitAll()")
    public String getFooter() {
        return "Footer";
    }
}

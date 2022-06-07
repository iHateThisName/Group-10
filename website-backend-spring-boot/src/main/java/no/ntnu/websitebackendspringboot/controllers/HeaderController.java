package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {
    @GetMapping(value = "/api/header")
    public String getHeader() {
        return "Header";
    }
}

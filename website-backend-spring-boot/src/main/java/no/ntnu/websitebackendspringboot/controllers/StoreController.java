package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StoreController {
    @RequestMapping("/store")
    @PreAuthorize("permitAll()")
    public String getStore() {
        return "store";
    }
}

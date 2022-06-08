package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StoreController {
    @RequestMapping("/store")
    public String getStore() {
        return "Store";
    }
}

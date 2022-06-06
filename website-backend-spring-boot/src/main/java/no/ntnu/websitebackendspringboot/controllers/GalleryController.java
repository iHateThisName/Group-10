package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {
    @GetMapping(value = "/gallery")
    @PreAuthorize("permitAll()")
    public  String getGallery() {
        return "Gallery";
    }
}

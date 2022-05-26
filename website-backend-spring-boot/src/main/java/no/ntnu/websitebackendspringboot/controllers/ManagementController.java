package no.ntnu.websitebackendspringboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
@Controller
public class ManagementController {

    //Only access if you are authenticated
    @GetMapping(value = "/management")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String getProfile() {
        return "Management";
    }
}

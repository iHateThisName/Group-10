package no.ntnu.websitebackendspringboot.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String getAccessDenied() {

        return "AccessDenied";}
}

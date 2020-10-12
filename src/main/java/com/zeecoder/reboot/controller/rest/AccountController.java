package com.zeecoder.reboot.controller.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @GetMapping
    public String getAll(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name;
        if (principal instanceof UserDetails) {
            name = ((UserDetails)principal).getUsername();
        } else {
            name = principal.toString();
        }

        String message = "Welcome " + name;
        model.addAttribute("message", message);
        return "admin-page";
    }
}

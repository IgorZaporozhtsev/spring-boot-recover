package com.zeecoder.reboot.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @GetMapping
    public String getAll(Model model) {
        return "admin-page";
    }
}

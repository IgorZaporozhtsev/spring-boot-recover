package com.zeecoder.reboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user_page")
    public String getUserPage(){
        return "userpage";
    }
}

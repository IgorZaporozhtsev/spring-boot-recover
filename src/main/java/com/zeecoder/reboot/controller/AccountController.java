package com.zeecoder.reboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Account account, Model model) {
        List<Account> accounts = service.getAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", account);

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

//    @PostMapping("/add")
//    public String addAccount(@Valid @ModelAttribute("account") Account account, BindingResult result) {
//        if (result.hasErrors()) {
//            return "redirect:/account";
//        }
//        service.add(account);
//        return "redirect:/account";
//    }

    @PostMapping("/add")
    public String addAccount(@Valid @ModelAttribute("account") Account account, BindingResult result, @RequestParam String role) throws JsonProcessingException {
        if (result.hasErrors()) {
            return "redirect:/account";
        }
        service.add(account, role);
        return "redirect:/account";
    }

    @PostMapping("/update")
    public String update(Account account, Model model) {
        service.update(account);
        model.addAttribute("accounts", service.getAll());
        return "redirect:/account";
    }

   /* @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id")  Long id) {
        service.delete(id);
        return "redirect:/account";
    }*/
}
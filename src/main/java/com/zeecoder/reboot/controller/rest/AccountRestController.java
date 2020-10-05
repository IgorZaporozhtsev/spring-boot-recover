package com.zeecoder.reboot.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.service.AccountServiceImpl;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountRestController {

    private final AccountServiceImpl service;

    @Autowired
    public AccountRestController(AccountServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = "/getAccounts")
    public List<Account> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> getOne(@PathVariable(value = "id")  Long id){
        Account account = service.getAccountById(id);
        return ResponseEntity.accepted().body(account);
    }

    @PostMapping("/add")
    public String addAccount(@Valid @ModelAttribute("account") Account account, BindingResult result, @RequestParam String role) throws JsonProcessingException {
        if (result.hasErrors()) {
            return "redirect:/account";
        }
        service.add(account, role);
        return "redirect:/account";
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody AccountDto dto) {
        service.update(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id")  Long id) {
        service.delete(id);
    }
}

package com.zeecoder.reboot.controller.rest;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRestController {

    private final AccountServiceImpl service;

    @Autowired
    public AccountRestController(AccountServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@RequestBody Account account)  { //todo ResponseEntity<String ???>
        service.add(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody Account account) {
        service.update(account);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id")  Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

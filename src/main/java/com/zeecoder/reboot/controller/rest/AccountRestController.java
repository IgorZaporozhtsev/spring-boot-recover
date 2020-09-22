package com.zeecoder.reboot.controller.rest;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

   /* @GetMapping(value = "/{id}")
    public ResponseEntity<Account> getOne(@PathVariable(value = "id")  Long id){
        Account account = service.getAccountById(id);
        return ResponseEntity.accepted().body(account);
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
       return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id")  Long id) {
        service.delete(id);
    }
}

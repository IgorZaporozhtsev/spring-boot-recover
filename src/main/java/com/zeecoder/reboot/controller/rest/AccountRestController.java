package com.zeecoder.reboot.controller.rest;

import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.exception.ApiRequestException;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public List<Account> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Account> getOne(@PathVariable(value = "id")  Long id){
        Account account = service.getAccountById(id).orElse(null);
        if (account == null){
            throw new ApiRequestException("User is not present");
        }
        return ResponseEntity.accepted().body(account);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public void addAccount(@RequestBody AccountDto dto){
        service.add(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody AccountDto dto) {
        service.update(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id")  Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

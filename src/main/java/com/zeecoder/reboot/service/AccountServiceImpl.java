
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public void add(Account account) {
        isHasRoleUser(account);
        repository.save(account);
    }

    private void isHasRoleUser(Account account) {
        Set<Role> roles = account.getRoles();

        Role userRole = new Role();
        userRole.setRole("USER");
        userRole.setAccount(account);

        if (!roles.contains(userRole)){ //need @EqualsAndHashCode
            roles.add(userRole);
            account.setRoles(roles);
        }
    }

    @Override
    public void update(Account account) {
        repository.save(account);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}


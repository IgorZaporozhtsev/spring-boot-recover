
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    //private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;

    @Autowired //@Lazy - resolve Circular Dependency https://www.baeldung.com/circular-dependencies-in-spring
    public AccountServiceImpl(/*@Lazy PasswordEncoder passwordEncoder, */AccountRepository repository) {
     //   this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public List<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public void add(Account account) {
        isHasRoleUser(account);
        // account.setPassword(passwordEncoder.encode(account.getPassword()));
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

    public Optional<Account> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void update(Account account) {
        repository.save(account);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Account findByName(String nickname) {
        return repository.findByNickname(nickname);
    }
}



package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;

    @Autowired //@Lazy - resolve Circular Dependency https://www.baeldung.com/circular-dependencies-in-spring
    public AccountServiceImpl(@Lazy PasswordEncoder passwordEncoder, AccountRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public List<Account> getAll() {
        return repository.findAll();
    }

    public void add(Account account) {
        //Set<Role> roles = account.getRoles();
        //roles.add(Role.USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        repository.save(account);
    }

    @Override
    public void add(Account account, String role) {
        //String[] roles = role.split(",");
        //var mySet = Set.of(roles);
        //account.setRoles(mySet);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        repository.save(account);
    }

    @Override
    public Optional<Account> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void update(Account account) {
        Account derivedAccount = repository.getOne(account.getId());
        derivedAccount = account;
        repository.save(derivedAccount);
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


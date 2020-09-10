
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import com.zeecoder.reboot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, RoleRepository roleRepository) {
        this.accountRepository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
        public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void add(Account account) {
        Account transientAccount = new Account(
                account.getFirst_name(),
                account.getNickname(),
                account.getPassword(),
                account.getEmail(),
                account.isActive()
        );

        Account derivedAccount = accountRepository.save(transientAccount);
        Role roleUser = roleRepository.findByRoleName("USER");

        derivedAccount.addRole(Objects.requireNonNullElseGet(roleUser, () -> new Role("USER")));

        for (Role accRole: account.getRoles()) {
            Role dbRole = roleRepository.findByRoleName(accRole.getRoleName());
            derivedAccount.addRole(Objects.requireNonNullElse(dbRole, accRole));
            accountRepository.save(derivedAccount);
        }
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}


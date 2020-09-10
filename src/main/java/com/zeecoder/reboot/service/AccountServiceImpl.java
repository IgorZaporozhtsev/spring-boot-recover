
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, RoleService roleService) {
        this.accountRepository = repository;
        this.roleService = roleService;
    }

    @Override
        public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void add(Account account) {

        Set<Role> roles = account.getRoles();
        Set<Role> toRemove = new HashSet<>();
        Set<Role> toAdd = new HashSet<>();

        Role userRole = new Role("USER");
        roles.add(userRole);
        account.setRoles(roles);

        //ConcurrentModificationException account.setRoles(roles);
        for (Role role : roles) {
            Role derivedRole = roleService.getRoleByName(role.getRoleName());
            if (derivedRole != null){
                toRemove.add(role);
                toAdd.add(derivedRole);
            }
        }

        roles.removeAll(toRemove);
        roles.addAll(toAdd);
        account.setRoles(roles);

        accountRepository.save(account);
    }

   @Override
    public void update(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    public Account getAccountById(Long id) {
        return accountRepository.getOne(id);
    }
}


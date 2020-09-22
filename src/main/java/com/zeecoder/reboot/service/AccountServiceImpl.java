
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(@Lazy PasswordEncoder passwordEncoder, AccountRepository repository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = repository;
        this.roleService = roleService;
    }

    @Override
        public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void add(Account account, String roleStr) {

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Set<Role> collectRolesToSet = collectRolesToSet(roleStr);
        account.setRoles(collectRolesToSet);

        Set<Role> roles = account.getRoles();
        Set<Role> toRemove = new HashSet<>();
        Set<Role> toAdd = new HashSet<>();

        Role userRole = new Role("USER");
        roles.add(userRole);
        account.setRoles(roles);

        //ConcurrentModificationException account.setRoles(roles);
        for (Role role : roles) {
            Role derivedRole = roleService.getRoleByName(role.getRoleName()); //todo don't retrieve data form Db in loop
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

    private Set<Role> collectRolesToSet(String roleStr){

        String[] roleStrings = roleStr.split("\\s*,\\s*");
        Set<Role> rolesSet = new HashSet<>();

        for (String s: roleStrings) {
            Role role = new Role();
            role.setRoleName(s);
            rolesSet.add(role);
        }

        return rolesSet;
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

    @Override
    public Account findByFirstName(String firstName) {
        return accountRepository.findByFirstName(firstName);
    }
}


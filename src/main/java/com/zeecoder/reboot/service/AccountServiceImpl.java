
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

    private final AccountRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
        public List<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public void add(Account account) {
        Account account1 = new Account(account.getFirst_name());
        Account savedAccount = repository.save(account1);

        List<Role> allRoles = roleRepository.findAll();

        for (Role role : allRoles) {
            for (Role accRole: account.getRoles()) {
                if (accRole.getRole().equals(role.getRole())){
                    savedAccount.getRoles().add(role);
                }
            }
            role.getAccounts().add(savedAccount);
            roleRepository.save(role);
        }

//        isHasRoleUser(account);
//        isHasRoleUser2(account);
//        isRoleAlreadyPersist(account);
    }

    private void isRoleAlreadyPersist(Account account) {
        List<Role> allRoles = roleRepository.findAll();
        Set<Role> roles = account.getRoles();
        Set<Role> dbRoles = new HashSet<>(allRoles);

        for (Role role : roles) {
            account.addRole(role);
        }
    }

    private void isHasRoleUser2(Account account) {
  /*      Set<Role> roles = account.getRoles();

        Role userRole = new Role();
        userRole.setRole("USER");
        userRole.setAccount(account);

        if (!roles.contains(userRole)){ //need @EqualsAndHashCode
            roles.add(userRole);
            account.setRoles(roles);
        }*/
    }
    private void isHasRoleUser(Account account) {
      /*  Set<Role> roles = account.getRoles();

        Role userRole = new Role();
        userRole.setRole("USER");
        userRole.setAccount(account);

        if (!roles.contains(userRole)){ //need @EqualsAndHashCode
            roles.add(userRole);
            account.setRoles(roles);
        }*/
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


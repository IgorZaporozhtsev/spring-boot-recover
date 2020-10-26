
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.config.AccountMapper;
import com.zeecoder.reboot.dto.AccountDto;
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
    private final AccountMapper mapper;


    @Autowired
    public AccountServiceImpl(@Lazy PasswordEncoder passwordEncoder, AccountRepository repository, RoleService roleService, AccountMapper accountMapper) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = repository;
        this.roleService = roleService;
        this.mapper = accountMapper;
    }

    @Override
        public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void add(AccountDto dto) {

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = dto.getRoles();
        Role userRole = new Role("USER");
        roles.add(userRole);
        Set<Role> addRoles = new HashSet<>();
        List<Role> allRoles = roleService.getAll();

        for (Role role1 : roles) {
            boolean matcher = false;
            for (Role role2: allRoles){
                if (role1.equals(role2)){
                    addRoles.add(role2);
                    matcher = true;
                }
            }
            if (!matcher){
                addRoles.add(role1);
            }
        }

        //Collections.disjoint() todo

        roles = addRoles;
        dto.setRoles(roles);

        accountRepository.save(mapper.toEntity(dto));
    }

    @Override
    public void update(AccountDto dto) { //todo переписать на lambda

        isPasswordWasChanged(dto);

        Set<Role> roles = dto.getRoles();
        Set<Role> addRoles = new HashSet<>();
        List<Role> allRoles = roleService.getAll();

        for (Role role1 : roles) {
            boolean coincidence = false;
            for (Role role2: allRoles){
                if (role1.getRoleName().equals(role2.getRoleName())){
                    addRoles.add(role2);
                    coincidence = true;
                }
            }
            if (!coincidence){
                addRoles.add(role1);
            }
        }

        roles = addRoles;
        dto.setRoles(roles);

        accountRepository.save(mapper.toEntity(dto));
    }

    private void isPasswordWasChanged(AccountDto dto) {
        Account derived = accountRepository.getOne(dto.getId());
        if (!derived.getPassword().equals(dto.getPassword())){
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account findByFirstName(String firstName) {
        return accountRepository.findByFirstName(firstName);
    }
}


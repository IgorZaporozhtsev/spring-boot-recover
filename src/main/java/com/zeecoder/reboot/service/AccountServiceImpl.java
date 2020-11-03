package com.zeecoder.reboot.service;

import com.zeecoder.reboot.config.AccountMapper;
import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Builder
@AllArgsConstructor
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

    @Transactional
    @Override
    public void add(AccountDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = dto.getRoles();
        Role userRole = new Role("USER");
        roles.add(userRole);
        AccountDto comparedDto = getComparedEntityWithDbData(dto, roles);
        accountRepository.save(mapper.toEntity(comparedDto));
    }

    @Transactional
    @Override
    public void update(AccountDto dto) { //todo переписать на lambda
        isPasswordWasChanged(dto);
        Set<Role> roles = dto.getRoles();
        AccountDto comparedDto = getComparedEntityWithDbData(dto, roles);
        accountRepository.save(mapper.toEntity(comparedDto));
    }

    private AccountDto getComparedEntityWithDbData(AccountDto dto, Set<Role> roles) {
        Set<Role> addRoles = new HashSet<>();
        List<Role> allRoles = roleService.getAll();

        for (Role role1 : roles) {           //Collections.disjoint() ?
            boolean coincidence = false;
            for (Role role2: allRoles){
                if (role1.equals(role2)){
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

        return dto;
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
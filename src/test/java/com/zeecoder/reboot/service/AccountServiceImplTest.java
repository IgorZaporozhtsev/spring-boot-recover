package com.zeecoder.reboot.service;

import com.zeecoder.reboot.config.AccountMapper;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class AccountServiceImplTest {


    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private RoleService roleService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AccountMapper accountMapper;

    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        accountService = AccountServiceImpl.builder()
            .accountRepository(accountRepository)
            .roleService(roleService)
            .passwordEncoder(passwordEncoder)
            .mapper(accountMapper)
            .build();
    }

    @Test
    public void shouldGetAllUsers(){

        Account tim = new Account("Tim", "Timerson", "tim", "timerson@gmail.com", true);

        List<Account> accounts = new ArrayList<>();
        accounts.add(tim);

        //mock repo (dao)
        given(accountRepository.findAll()).willReturn(accounts);

        List<Account> all = accountService.getAll();
        assertThat(all).hasSize(1);

        Account account = all.get(0);
        assertThat(account.getFirstName()).isEqualTo("Tim");
        assertThat(account.getNickname()).isEqualTo("Timerson");
        assertThat(account.getPassword()).isEqualTo("tim");
        assertThat(account.getEmail()).isEqualTo("timerson@gmail.com");
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void findByFirstName() {
    }
}
package com.zeecoder.reboot.service;

import com.zeecoder.reboot.config.AccountMapper;
import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.MalformedObjectNameException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    public void shouldNotNotNullGetAllUsers(){

        Account tim = new Account(100L, "Tim", "Timerson", "tim", "timerson@gmail.com", true, new HashSet<Role>());

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
    public void shouldAddedToDb(){
//        Account tim = new Account(100L, "tim", "timerson", "tim", "timerson@gmail.com", true, new HashSet<Role>());
//        AccountDto accountDto = AccountMapper.toDto(tim);
        AccountDto account = mock(AccountDto.class);
        accountService.add(account);
        verify(accountService, times(1)).add(account);
    }

    @Test
    public void shouldDeletedFromDb(){
        Account deletedAccount = mock(Account.class);
        accountService.delete(deletedAccount.getId());
        verify(accountRepository, times(1)).deleteById(deletedAccount.getId());
    }

    @Test
    public void shouldSelectedAccountById(){
        AccountDto account = mock(AccountDto.class);
        accountService.add(account);

        Optional<Account> timById = accountService.getAccountById(account.getId());
        assertThat(timById.isPresent()).isTrue();


    }
}
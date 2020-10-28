package com.zeecoder.reboot;

import com.zeecoder.reboot.repository.AccountRepository;
import com.zeecoder.reboot.service.AccountService;
import com.zeecoder.reboot.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
public class AccountServiceTest {



    @MockBean
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        accountService = AccountServiceImpl.builder()
            .accountRepository(accountRepository).build();
    }

    @Test
    public void ShouldGetAllUsers(){
        int res= 2 + 1;
        assertThat(res).isEqualTo(3);
    }

}

package com.zeecoder.reboot.service;

import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import java.util.List;

public interface AccountService {

    List<Account> getAll();

    void add(AccountDto dto);

    void update(AccountDto dto);

    void delete(Long id);

    Account getAccountById(Long id);

    Account findByFirstName(String firstName);
}

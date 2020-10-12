package com.zeecoder.reboot.service;

import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAll();

    void add(AccountDto dto);

    void update(AccountDto dto);

    void delete(Long id);

    Optional<Account> getAccountById(Long id);

    Account findByFirstName(String firstName);
}

package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Account;
import java.util.List;

public interface AccountService {

    List<Account> getAll();

    //void add(Account account);

    void add(Account account, String role);

    void update(Account account);

    void delete(Long id);

    Account getAccountById(Long id);

    Account findByFirstName(String firstName);
}

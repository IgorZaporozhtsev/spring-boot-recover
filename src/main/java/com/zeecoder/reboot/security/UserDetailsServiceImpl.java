/*
package com.zeecoder.reboot.security;

import com.zeecoder.reboot.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserDetails derivedAccount = accountService.findByName(name);

        if (derivedAccount == null){
            throw new UsernameNotFoundException("User '" + name + "' not found");
        }

        return new User(derivedAccount.getUsername(), derivedAccount.getPassword(), derivedAccount.getAuthorities());
    }
}
*/

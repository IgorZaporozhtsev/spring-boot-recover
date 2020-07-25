package com.zeecoder.reboot.repository;

import com.zeecoder.reboot.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNickname(String nickname);
}

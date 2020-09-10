package com.zeecoder.reboot.repository;

import com.zeecoder.reboot.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}

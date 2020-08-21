package com.zeecoder.reboot.repository;

import com.zeecoder.reboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole (String role);
}

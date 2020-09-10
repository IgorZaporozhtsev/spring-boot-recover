package com.zeecoder.reboot.repository;

import com.zeecoder.reboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName (String role);
}

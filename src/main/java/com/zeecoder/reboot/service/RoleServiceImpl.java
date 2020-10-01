package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getRoleByName(String role){
        return repository.findByRoleName(role);
    }
}

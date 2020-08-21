package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.RoleRepository;

public class RoleServiceImpl {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getRoleByName(String role){
        return repository.findByRole(role);
    }
}

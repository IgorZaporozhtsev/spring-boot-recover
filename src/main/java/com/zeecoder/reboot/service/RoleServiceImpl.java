package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Role;
import com.zeecoder.reboot.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    public Role getRoleByName(String role){
        return repository.findByRoleName(role);
    }
}

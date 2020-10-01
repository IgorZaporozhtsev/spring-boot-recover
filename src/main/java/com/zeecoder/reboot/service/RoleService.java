package com.zeecoder.reboot.service;

import com.zeecoder.reboot.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role getRoleByName(String role);
}

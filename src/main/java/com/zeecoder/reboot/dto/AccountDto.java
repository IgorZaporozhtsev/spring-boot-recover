package com.zeecoder.reboot.dto;

import com.zeecoder.reboot.model.Role;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String firstName;
    private String nickname;
    private String password;
    private String email;
    private boolean active;
    private Set<Role> roles;
}

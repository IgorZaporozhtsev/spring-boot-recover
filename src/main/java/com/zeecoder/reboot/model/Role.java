package com.zeecoder.reboot.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "role")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@roleId")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String role;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @Override
    public String getAuthority() {
        return getRole();
    }
}
package com.zeecoder.reboot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "role")
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
package com.zeecoder.reboot.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    private Set<Account> accounts = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
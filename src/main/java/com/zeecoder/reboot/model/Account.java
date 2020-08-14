package com.zeecoder.reboot.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "account")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@accountId")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String first_name;
    @Column
    private String nickname;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
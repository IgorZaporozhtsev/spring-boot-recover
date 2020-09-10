package com.zeecoder.reboot.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "account")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String nickname;
    @NotNull(message = "mustn't null")
    @NotEmpty(message = "mustn't empty")
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private boolean active;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},  fetch = FetchType.EAGER)
    //@JoinTable describe intermediate table
    @JoinTable(name = "account_to_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public Account(
            String firstName,
            String nickname,
            String password,
            String email,
            boolean active) {
        this.firstName = firstName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.active = active;
    }
}
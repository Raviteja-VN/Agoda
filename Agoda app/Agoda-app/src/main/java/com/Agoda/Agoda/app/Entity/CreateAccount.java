package com.Agoda.Agoda.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Table(name="create_accounts")
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="email",nullable = false, unique = true)
    private String email;

    @Column(name="phone",nullable = false)
    private String phone;

    @Column(name="password",nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "createAccount_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

}

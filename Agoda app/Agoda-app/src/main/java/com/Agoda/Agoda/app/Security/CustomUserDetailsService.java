package com.Agoda.Agoda.app.Security;

import com.Agoda.Agoda.app.Entity.CreateAccount;
import com.Agoda.Agoda.app.Entity.Role;
import com.Agoda.Agoda.app.Repository.CreateAccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private CreateAccountRepository createAccountRepository;
    public CustomUserDetailsService(CreateAccountRepository createAccountRepository) {
        this.createAccountRepository = createAccountRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String firstNameOrEmail) throws UsernameNotFoundException {
        CreateAccount createAccount = createAccountRepository.findByFirstNameOrEmail(firstNameOrEmail, firstNameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with  email:" + firstNameOrEmail));

        return new org.springframework.security.core.userdetails.User(createAccount.getEmail(),
                createAccount.getPassword(), mapRolesToAuthorities(createAccount.getRoles()));
    }
    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}


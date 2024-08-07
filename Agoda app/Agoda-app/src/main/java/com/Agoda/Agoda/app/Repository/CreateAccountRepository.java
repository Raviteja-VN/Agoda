package com.Agoda.Agoda.app.Repository;

import com.Agoda.Agoda.app.Entity.CreateAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreateAccountRepository extends JpaRepository<CreateAccount,Long> {

    Optional<CreateAccount> findByFirstNameOrEmail(String firstName, String email);
    Boolean existsByEmail(String email);
}

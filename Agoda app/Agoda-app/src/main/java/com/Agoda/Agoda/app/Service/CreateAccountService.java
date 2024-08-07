package com.Agoda.Agoda.app.Service;

import com.Agoda.Agoda.app.Payload.CreateAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreateAccountService {


       CreateAccountDto newAccount(CreateAccountDto createAccountDto) ;

       Page<CreateAccountDto> getAllAccounts(Pageable pageable);

       CreateAccountDto getAccountById(long id);

       CreateAccountDto updateAccount(CreateAccountDto createAccountDto, long id);

       void deleteAccountById(long id);
}

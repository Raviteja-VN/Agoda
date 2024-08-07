package com.Agoda.Agoda.app.ServiceImpl;

import com.Agoda.Agoda.app.Entity.CreateAccount;
import com.Agoda.Agoda.app.Entity.Hotel;
import com.Agoda.Agoda.app.Exception.ResourceNotFoundException;
import com.Agoda.Agoda.app.Payload.CreateAccountDto;
import com.Agoda.Agoda.app.Payload.HotelDto;
import com.Agoda.Agoda.app.Repository.CreateAccountRepository;
import com.Agoda.Agoda.app.Service.CreateAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CreateAccountServiceImpl implements CreateAccountService {
    @Autowired
    private CreateAccountRepository createAccountRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateAccountServiceImpl(CreateAccountRepository createAccountRepository, ModelMapper modelMapper) {
        this.createAccountRepository = createAccountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateAccountDto newAccount(CreateAccountDto createAccountDto) {
        CreateAccount createAccount=new CreateAccount();
        createAccount.setFirstName(createAccountDto.getFirstName());
        createAccount.setLastName(createAccountDto.getLastName());
        createAccount.setEmail(createAccountDto.getEmail());
        createAccount.setPhone(createAccountDto.getPhone());
        createAccount.setPassword(passwordEncoder.encode(createAccountDto.getPassword()));

        CreateAccount newAccount = createAccountRepository.save(createAccount);

        CreateAccountDto dto = new CreateAccountDto();
        dto.setId(newAccount.getId());
        dto.setFirstName(newAccount.getFirstName());
        dto.setLastName(newAccount.getLastName());
        dto.setEmail(newAccount.getEmail());
        dto.setPhone(newAccount.getPhone());
        dto.setPassword(passwordEncoder.encode(newAccount.getPassword()));

        return dto;
    }

    @Override
    public Page<CreateAccountDto> getAllAccounts(Pageable pageable) {
        Page<CreateAccount> accountPage= createAccountRepository.findAll(pageable);
        List<CreateAccountDto> accountDto= accountPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(accountDto,pageable,accountPage.getTotalElements());
    }

    @Override
    public CreateAccountDto getAccountById(long id) {
        CreateAccount createAccount = createAccountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Id", id)
        );
        return  mapToDto(createAccount);
    }

    @Override
    public CreateAccountDto updateAccount(CreateAccountDto createAccountDto, long id) {
        CreateAccount createAccount = createAccountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Id", id)
        );

        createAccount.setFirstName(createAccountDto.getFirstName());
        createAccount.setLastName(createAccountDto.getLastName());
        createAccount.setEmail(createAccountDto.getEmail());
        createAccount.setPhone(createAccountDto.getPhone());
        createAccount.setPassword(passwordEncoder.encode(createAccountDto.getPassword()));

        CreateAccount updatedAccount = createAccountRepository.save(createAccount);

        return mapToDto( updatedAccount);
    }

    @Override
    public void deleteAccountById(long id) {
        createAccountRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Account","Id",id)
        );
        createAccountRepository.deleteById(id);
    }

    CreateAccountDto mapToDto(CreateAccount createAccount) {
        CreateAccountDto createAccountDto=modelMapper.map(createAccount, CreateAccountDto.class);
        return createAccountDto;
    }

    CreateAccount mapToEntity(CreateAccountDto createAccountDto) {
        CreateAccount createAccount=modelMapper.map(createAccountDto, CreateAccount.class);
        return createAccount;
    }

}

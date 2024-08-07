package com.Agoda.Agoda.app.Controller;

import com.Agoda.Agoda.app.Payload.CreateAccountDto;
import com.Agoda.Agoda.app.Service.CreateAccountService;
import com.Agoda.Agoda.app.Utils.Email;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class CreateAccountController {


    private final CreateAccountService createAccountService;

    @Autowired
    private Email email;

    public CreateAccountController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    //http://localhost:8080/api/accounts/new
    @PostMapping("/new")
    public ResponseEntity<CreateAccountDto> newAccount(@RequestBody CreateAccountDto createAccountDto) {
        CreateAccountDto newAccount = createAccountService.newAccount(createAccountDto);
        email.sendEmail(createAccountDto.getEmail(),"Account Created","Account created successfully.\n"
                +"\n"+ "ID: " + newAccount.getId() + "\n" +
                        "Email: " + newAccount.getEmail());
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/accounts/getAll?page=1&size=2&sort=id,asc
    @GetMapping("/getAll")
    public ResponseEntity<Page<CreateAccountDto>> getAllAccounts(@PageableDefault(size = 2,sort="id") Pageable pageable){
        Page<CreateAccountDto> accountPage=   createAccountService.getAllAccounts(pageable);
        return new ResponseEntity<>(accountPage,HttpStatus.OK);

    }
    //http://localhost:8080/api/accounts/id
    @GetMapping("/{id}")
    public ResponseEntity<CreateAccountDto>getAccountById(@PathVariable("id") long id){
        return new ResponseEntity<>(createAccountService.getAccountById(id),HttpStatus.OK);

    }
    //http://localhost:8080/api/accounts/update/id
    @PutMapping("/update/{id}")
    public ResponseEntity<CreateAccountDto>updateAccount(@RequestBody CreateAccountDto createAccountDto,@PathVariable("id") long id){
        CreateAccountDto updatedDto =  createAccountService.updateAccount(createAccountDto,id);
        return  ResponseEntity.ok(updatedDto);
    }
    //http://localhost:8080/api/accounts/id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("id") long id){
        createAccountService.deleteAccountById(id);
        return new ResponseEntity<String>("Account Successfully deleted",HttpStatus.OK);
    }
}

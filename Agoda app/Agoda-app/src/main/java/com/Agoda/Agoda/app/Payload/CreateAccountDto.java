package com.Agoda.Agoda.app.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Size(max=10,message ="Phone must be 10 character ")
    private String phone;
    @Size(min=6,max=15,message ="Password must be between 6 to 15 character ")
    private String password;

}

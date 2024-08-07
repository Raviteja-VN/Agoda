package com.Agoda.Agoda.app.Controller;

import com.Agoda.Agoda.app.Payload.JwtAuthResponse;
import com.Agoda.Agoda.app.Payload.LoginDto;
import com.Agoda.Agoda.app.Repository.CreateAccountRepository;
import com.Agoda.Agoda.app.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CreateAccountRepository createAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    //http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
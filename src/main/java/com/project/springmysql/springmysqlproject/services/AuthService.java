package com.project.springmysql.springmysqlproject.services;

import com.project.springmysql.springmysqlproject.dto.security.AccountCredentialsDTO;
import com.project.springmysql.springmysqlproject.dto.security.TokenDTO;
import com.project.springmysql.springmysqlproject.repositories.UserRepository;
import com.project.springmysql.springmysqlproject.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;


    @SuppressWarnings("rawtypes")
    public ResponseEntity signIn(AccountCredentialsDTO data) {
        try{
            var userName = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            var user = repository.findByUsername(userName);

            var tokenResponse = new TokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(userName, user.getRoles());
            }
            else{
                throw new UsernameNotFoundException("Username " + userName  + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        }
        catch (Exception e){
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenDTO();


            if (user != null) {
                tokenResponse = tokenProvider.refreshToken(refreshToken);
            }
            else{
                throw new UsernameNotFoundException("Username " + username  + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);


    }
}

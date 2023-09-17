package com.example.identityservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.entity.UserCredential;
import com.example.identityservice.helper.ResponeHandler;
import com.example.identityservice.repository.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * 
     * @param auth
     * @return
     */

    public ResponseEntity<Object> login(AuthRequest auth) {
        boolean isPresent = repository.findByEmail(auth.getEmail()).isPresent();
        if (!isPresent) {
            return ResponeHandler.generateResponse("User Is Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        } else {
            UserCredential user = repository.findByEmail(auth.getEmail()).get();

            if (passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("data", user);
                map.put("token", this.generateToken(auth.getEmail()));
                map.put("message", "Login Successfully");
                map.put("status", HttpStatus.OK.value());

                return new ResponseEntity<Object>(map, HttpStatus.OK);
            } else {
                return ResponeHandler.generateResponse("Wrong User Name Or Password", HttpStatus.EXPECTATION_FAILED,
                        null);
            }
        }
    }

    /**
     * 
     * @param credential
     * @return
     */
    public ResponseEntity<Object> saveUser(UserCredential credential) {
        boolean isPresent = repository.findByEmail(credential.getEmail()).isPresent();
        if (!isPresent) {
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));

            UserCredential user = repository.save(credential);

            return ResponeHandler.generateResponse("User Added Successfully", HttpStatus.OK, user);
        }
        return ResponeHandler.generateResponse("User Is Already Exist", HttpStatus.EXPECTATION_FAILED, null);
    }

    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}

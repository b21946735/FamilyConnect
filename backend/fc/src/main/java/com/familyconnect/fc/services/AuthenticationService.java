package com.familyconnect.fc.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.familyconnect.fc.models.ApplicationUser;
import com.familyconnect.fc.models.LoginResponseDTO;
import com.familyconnect.fc.models.Role;
import com.familyconnect.fc.repository.RoleRepository;
import com.familyconnect.fc.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ApplicationUser registerUser(String username,String name, String password, String role){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role).get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        

        if(userRepository.findByUsername(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if(username == null || username.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username, password and name cannot be empty");
        }


        System.out.println("User registered: " + username + " " + name +  " " + role);

        return userRepository.save(new ApplicationUser( username, name, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}

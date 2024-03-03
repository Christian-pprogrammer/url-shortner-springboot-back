package com.irembo.test.services;

import com.irembo.test.dto.BearerToken;
import com.irembo.test.dto.LoginDto;
import com.irembo.test.dto.RegisterDto;
import com.irembo.test.exceptions.UnAuthorizedException;
import com.irembo.test.exceptions.UrlNotFoundException;
import com.irembo.test.models.User;
import com.irembo.test.persistence.IUserRepository;
import com.irembo.test.security.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final AuthenticationManager authenticationManager ;
    private final IUserRepository iUserRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;


    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        try{
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            //By Default , he/she is a simple user
            iUserRepository.save(user);
            String token = jwtUtilities.generateToken(registerDto.getUsername());
            return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);
        }catch (DataIntegrityViolationException e) {
            throw new UnAuthorizedException("Username already taken");
        }
    }

    @Override
    public BearerToken authenticate(LoginDto loginDto) {

        System.out.println("call auth manager");
        try {
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            System.out.println("security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("get user");
            User user = iUserRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            System.out.println("finish get user");
            String token = jwtUtilities.generateToken(user.getUsername());
            BearerToken bt = new BearerToken(token, "Bearer");
            return bt;
        }catch (AuthenticationException e) {
            System.out.println("Auth exception");
            throw new UnAuthorizedException();
        }
    }

}


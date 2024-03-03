package com.irembo.test.services;


import com.irembo.test.dto.BearerToken;
import com.irembo.test.dto.LoginDto;
import com.irembo.test.dto.RegisterDto;
import com.irembo.test.models.User;
import org.springframework.http.ResponseEntity;


public interface IUserService {

   BearerToken authenticate(LoginDto loginDto);
   ResponseEntity<?> register (RegisterDto registerDto);
}

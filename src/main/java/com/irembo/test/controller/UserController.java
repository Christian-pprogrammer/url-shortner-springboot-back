package com.irembo.test.controller;


import com.irembo.test.dto.BearerToken;
import com.irembo.test.services.IUserService;
import com.irembo.test.dto.LoginDto;
import com.irembo.test.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final IUserService iUserService ;
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto)
    { return  iUserService.register(registerDto);}

    @PostMapping("/login")
    public ResponseEntity<BearerToken> authenticate(@RequestBody LoginDto loginDto)
    {
        BearerToken bt = iUserService.authenticate(loginDto);
        return new ResponseEntity<BearerToken>(bt, HttpStatus.OK);
    }
}

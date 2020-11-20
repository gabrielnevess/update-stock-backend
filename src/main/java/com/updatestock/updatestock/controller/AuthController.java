package com.updatestock.updatestock.controller;

import javax.validation.Valid;
import com.updatestock.updatestock.dto.TokenDto;
import com.updatestock.updatestock.dto.UserLoginDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws BadRequestException {
        TokenDto tokenDto = authService.login(userLoginDto);
        return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);
    }

}
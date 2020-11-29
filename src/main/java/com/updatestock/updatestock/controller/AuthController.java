package com.updatestock.updatestock.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.updatestock.updatestock.dto.PasswordDto;
import com.updatestock.updatestock.dto.TokenDto;
import com.updatestock.updatestock.dto.UserForgotPasswordDto;
import com.updatestock.updatestock.dto.UserLoginDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login no Sistema")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws BadRequestException {
        TokenDto tokenDto = this.authService.login(userLoginDto);
        return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Esqueci minha senha")
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody UserForgotPasswordDto UserPasswordResetDto,
            HttpServletRequest request) throws NotFoundException, MessagingException, IOException, TemplateException {
        this.authService.forgotPassword(UserPasswordResetDto, request);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }

    @ApiOperation(value = "Atualização de Senha")
    @RequestMapping(value = "/passwordReset", method = RequestMethod.POST)
    public ResponseEntity<Boolean> passwordReset(@Valid @RequestBody PasswordDto passwordDto)
            throws BadRequestException, NotFoundException {
        this.authService.passwordReset(passwordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
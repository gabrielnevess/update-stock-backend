package com.updatestock.updatestock.service;

import java.util.Optional;
import com.updatestock.updatestock.dto.TokenDto;
import com.updatestock.updatestock.dto.UserLoginDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.jwt.JwtTokenUtil;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public TokenDto login(UserLoginDto userLoginDto) throws BadRequestException {
        Optional<User> optionalUser = this.userRepository.findByLogin(userLoginDto.getLogin());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (optionalUser.isPresent()) {
            if (optionalUser.get().getActive()) {
                if (bCryptPasswordEncoder.matches(userLoginDto.getPassword(), optionalUser.get().getPassword())) {
                    TokenDto tokenDto = jwtTokenUtil.generateToken(optionalUser.get());
                    return tokenDto;
                } else {
                    throw new BadRequestException("Senha Incorreta.");
                }
            } else {
                throw new BadRequestException("A sua conta está desativada. Contate o administrador.");
            }
        } else {
            throw new BadRequestException("Usuário não cadastrado.");
        }
    }

}
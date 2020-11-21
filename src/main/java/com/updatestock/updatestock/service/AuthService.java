package com.updatestock.updatestock.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import com.updatestock.updatestock.dto.PasswordDto;
import com.updatestock.updatestock.dto.TokenDto;
import com.updatestock.updatestock.dto.UserForgotPasswordDto;
import com.updatestock.updatestock.dto.UserLoginDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.jwt.JwtTokenUtil;
import com.updatestock.updatestock.model.Mail;
import com.updatestock.updatestock.model.PasswordReset;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.PasswordResetRepository;
import com.updatestock.updatestock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import freemarker.template.TemplateException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private MailService mailService;

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

    /**
     * 
     * Esqueci a senha / evnio de email
     * 
     * @param email
     * @param request
     * @throws NotFoundException
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    public void forgotPassword(UserForgotPasswordDto userPasswordResetDto, HttpServletRequest request)
            throws NotFoundException, MessagingException, IOException, TemplateException {
        User user = this.userRepository.findByEmail(userPasswordResetDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        String token = UUID.randomUUID().toString();
        PasswordReset passwordReset = new PasswordReset(token, user);
        this.passwordResetRepository.save(passwordReset);

        String appUrl = request.getScheme() + "://" + request.getServerName();

        Mail mail = new Mail();
        mail.setFrom(this.environment.getProperty("support.email"));
        mail.setTo(user.getEmail());
        mail.setSubject("Redefinição de Senha");

        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("url", appUrl + "/redefinicao-senha?token=" + token);
        mail.setModel(model);

        this.mailService.sendSimpleMessage(mail, "password_reset_template.ftlh");
    }

    /**
     * 
     * Resetar a senha do usuário
     * 
     * @param passwordDto
     * @return
     * @throws BadRequestException
     * @throws NotFoundException
     */
    public void passwordReset(PasswordDto passwordDto) throws BadRequestException, NotFoundException {
        PasswordReset passwordReset = this.passwordResetRepository.findByToken(passwordDto.getToken())
                .orElseThrow(() -> new NotFoundException("Token Inválido!"));

        String result = isTokenExpiredOrUsed(passwordReset);
        if (result.equals("expired")) {
            throw new BadRequestException("Token expirado.");

        } else if (result.equals("used")) {
            throw new BadRequestException("Token já utilizado.");

        } else if (result.equals("valid")) {
            User user = passwordReset.getUser();
            if (user != null) {
                passwordReset.setUsedToken(true);
                this.passwordResetRepository.save(passwordReset);
                user.setPassword(new BCryptPasswordEncoder().encode(passwordDto.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new BadRequestException("Usuário não encontrado.");
            }
        }

    }

    /**
     * 
     * verificar se o token é válido
     * 
     * @param passToken
     * @return
     */
    public String isTokenExpiredOrUsed(PasswordReset passToken) {
        Calendar cal = Calendar.getInstance();
        Boolean isExpired = passToken.getExpiryDate().before(cal.getTime());
        if (isExpired) {
            return "expired";
        } else if (passToken.getUsedToken()) {
            return "used";
        }
        return "valid";
    }

}
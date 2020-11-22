package com.updatestock.updatestock.service;

import java.security.Principal;
import java.util.Optional;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String MESSAGE_BAD_REQUEST_EMAIL = "Já existe um usuário com este E-mail.";
    private static final String MESSAGE_BAD_REQUEST_LOGIN = "Já existe um usuário com este Login.";

    @Autowired
    private UserRepository userRepository;

    public User save(User u) throws BadRequestException {

        // verificando se há algum email igual na base de dados
        if (this.userRepository.findByEmail(u.getEmail()).isPresent()) {
            throw new BadRequestException(MESSAGE_BAD_REQUEST_EMAIL);
        }

        // verificando se há algum login igual na base de dados
        if (this.userRepository.findByLogin(u.getLogin()).isPresent()) {
            throw new BadRequestException(MESSAGE_BAD_REQUEST_LOGIN);
        }

        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        return userRepository.save(u);
    }

    public User update(User u, Principal principal) throws NotFoundException, BadRequestException {
        User user = this.userRepository.findByLogin(principal.getName())
                        .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o login :: %s", principal.getName())));
        if (user.getId() == u.getId()) {
            throw new BadRequestException("Não é possível executar essa ação!");

        } else {
            user = this.userRepository.findById(u.getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o id :: %s", u.getId())));

            // verificando se o e-mail da base de dados é diferente do que está sendo
            // passado pelo usuário
            if (!user.getEmail().equalsIgnoreCase(u.getEmail())) {
                if (this.userRepository.findByEmail(u.getEmail()).isPresent()) {
                    throw new BadRequestException(MESSAGE_BAD_REQUEST_EMAIL);
                }
            }

            // verificando se o login da base de dados é diferente do que está sendo passado
            // pelo usuário
            if (!user.getLogin().equalsIgnoreCase(u.getLogin())) {
                if (this.userRepository.findByLogin(u.getLogin()).isPresent()) {
                    throw new BadRequestException(MESSAGE_BAD_REQUEST_LOGIN);
                }
            }

            user.setEmail(u.getEmail());
            user.setLogin(u.getLogin());
            user.setPassword(u.getPassword());
            user.setName(u.getName());
            user.setActive(u.getActive());

            return userRepository.save(user);
        }
    }

    public User findById(Integer id, Principal principal) throws NotFoundException, BadRequestException {
        Optional<User> optionalUser = this.userRepository.findByLogin(principal.getName());
        if (optionalUser.get().getId() == id) {
            throw new BadRequestException("Não é possível executar essa ação!");

        } else {
            return this.userRepository.findById(id)
                       .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o id :: %d", id)));
        }
    }

    public User findById(Principal principal) throws NotFoundException {
        return this.userRepository.findByLogin(principal.getName())
                   .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o login :: %s", principal.getName())));
    }

}
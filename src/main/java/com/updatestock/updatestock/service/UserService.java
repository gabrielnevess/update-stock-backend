package com.updatestock.updatestock.service;

import java.security.Principal;
import java.util.Optional;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User u) throws BadRequestException {
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
                    
            user.setEmail(u.getEmail());
            user.setLogin(u.getLogin());
            user.setPassword(u.getPassword());
            user.setName(u.getName());
            user.setActive(u.getActive());

            return userRepository.save(user);
        }
    }

    public void delete(Integer id, Principal principal) throws NotFoundException, BadRequestException {
        Optional<User> optionalUser = this.userRepository.findByLogin(principal.getName());
        if (optionalUser.get().getId() == id) {
            throw new BadRequestException("Não é possível executar essa ação!");

        } else {
            User user = this.userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o id :: ", id)));
            try {
                this.userRepository.delete(user);
            } catch (Exception ex) {
                throw new BadRequestException(
                        String.format("O usuário com o id :: %d não pôde ser deletada, pois está em uso!", id));
            }
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

    public Page<User> findAll(int page, int size) {
        return this.userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<User> findAllUsers(Principal principal, int page, int size) throws NotFoundException {
        User user = this.userRepository.findByLogin(principal.getName())
                        .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o login :: %s", principal.getName())));
        return this.userRepository.findAllByNotUser_Id(user.getId(), PageRequest.of(page, size));
    }

}
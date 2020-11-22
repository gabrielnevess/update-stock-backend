package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;
import com.updatestock.updatestock.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole save(UserRole ur) {
        return this.userRoleRepository.save(ur);
    }

    public UserRole update(UserRole ur) throws NotFoundException {
        UserRole userRole = this.userRoleRepository.findById(ur.getUserRoleId())
                          .orElseThrow(() -> new NotFoundException(String.format("Usuário Permissão não encontrada com o id :: %d e %d", ur.getUserRoleId().getUserId(), ur.getUserRoleId().getRoleId())));
        userRole.setUserRoleId(ur.getUserRoleId());
        return this.userRoleRepository.save(userRole);
    }

    public void delete(UserRoleId ur) throws NotFoundException {
        UserRole userRole = this.userRoleRepository.findById(ur)
                          .orElseThrow(() -> new NotFoundException(String.format("Usuário Permissão não encontrada com o id :: %d e %d", ur.getUserId(), ur.getRoleId())));
        this.userRoleRepository.delete(userRole);
    }

    public UserRole findById(UserRoleId ur) throws NotFoundException {
        return this.userRoleRepository.findById(ur)
                   .orElseThrow(() -> new NotFoundException(String.format("Usuário Permissão não encontrada com o id :: %d e %d", ur.getUserId(), ur.getRoleId())));
    }

    public Page<UserRole> findAll(int page, int size) {
        return this.userRoleRepository.findAll(PageRequest.of(page, size));
    }

}
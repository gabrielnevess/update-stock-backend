package com.updatestock.updatestock.service;

import java.util.List;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Role;
import com.updatestock.updatestock.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role r) {
        return this.roleRepository.save(r);
    }

    public Role update(Role r) throws NotFoundException {
        Role role = this.roleRepository.findById(r.getId())
                          .orElseThrow(() -> new NotFoundException(String.format("Permissão não encontrada com o id :: %d", r.getId())));
        role.setName(r.getName());
        return this.roleRepository.save(role);
    }

    public void delete(Integer id) throws NotFoundException, BadRequestException {
        Role role = this.roleRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Permissão não encontrada com o id :: %d", id)));
        try {
            this.roleRepository.delete(role);
        } catch(Exception ex) {
            throw new BadRequestException(String.format("A permissão com o id :: %d não pôde ser deletada, pois está em uso!", id));
        }
    }

    public Role findById(Integer id) throws NotFoundException {
        return this.roleRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Permissão não encontrada com o id :: %d", id)));
    }

    public Page<Role> findAll(int page, int size) {
        return this.roleRepository.findAll(PageRequest.of(page, size));
    }

    public List<Role> findAllRoles() {
        return (List<Role>) this.roleRepository.findAll();
    }

}

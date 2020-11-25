package com.updatestock.updatestock.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.updatestock.updatestock.dto.UserRolesDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Role;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;
import com.updatestock.updatestock.repository.UserRepository;
import com.updatestock.updatestock.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public Boolean saveTransferList(Principal principal, Integer userId, Map<String, List<UserRolesDto>> map) throws NotFoundException, BadRequestException {

        User user = this.userRepository.findByLogin(principal.getName())
                        .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o login :: %s", principal.getName())));
        if (user.getId() == userId) {
            throw new BadRequestException("Não é possível executar essa ação!");

        } else {

            try {
                List<UserRolesDto> left = map.get("left");
                List<UserRolesDto> right = map.get("right");

                if (left.size() > 0) {
                    List<UserRole> userRoles = new ArrayList<>();
                    for (UserRolesDto ur : left) {
                        UserRole userRole = new UserRole();

                        UserRoleId userRoleId = new UserRoleId();
                        userRoleId.setUserId(ur.getUserId());
                        userRoleId.setRoleId(ur.getRoleId());

                        userRole.setUserRoleId(userRoleId);
                        userRoles.add(userRole);
                    }
                    userRoleRepository.deleteAll(userRoles);
                }

                if (right.size() > 0) {
                    List<UserRole> userRoles = new ArrayList<>();
                    for (UserRolesDto ur : right) {
                        UserRole userRole = new UserRole();

                        UserRoleId userRoleId = new UserRoleId();
                        userRoleId.setUserId(ur.getUserId());
                        userRoleId.setRoleId(ur.getRoleId());

                        userRole.setUserRoleId(userRoleId);
                        userRoles.add(userRole);
                    }
                    userRoleRepository.saveAll(userRoles);
                }

                return true;
            } catch (Exception ex) {
                throw new BadRequestException("Erro ao salvar as permissões!");
            }
        }
    }

    public void delete(UserRoleId ur) throws NotFoundException {
        UserRole userRole = this.userRoleRepository.findById(ur)
                          .orElseThrow(() -> new NotFoundException(String.format("Usuário Permissão não encontrada com o id :: %d e %d", ur.getUserId(), ur.getRoleId())));
        this.userRoleRepository.delete(userRole);
    }

    public UserRole findById(UserRoleId ur) throws NotFoundException, BadRequestException {
            return this.userRoleRepository.findById(ur)
                       .orElseThrow(() -> new NotFoundException(String.format("Usuário Permissão não encontrada com o id :: %d e %d", ur.getUserId(), ur.getRoleId())));
    }

    public Page<UserRole> findAll(int page, int size) {
        return this.userRoleRepository.findAll(PageRequest.of(page, size));
    }

    public Map<String, List<UserRolesDto>> findTransferList(Principal principal, Integer userId) throws NotFoundException, BadRequestException {

        User user = this.userRepository.findByLogin(principal.getName())
                        .orElseThrow(() -> new NotFoundException(String.format("Usuário não encontrado com o login :: %s", principal.getName())));
        if (user.getId() == userId) {
            throw new BadRequestException("Não é possível executar essa ação!");

        } else {

            Map<String, List<UserRolesDto>> transferList = new HashMap<>();

            List<UserRolesDto> right = this.userRoleRepository.findByListRight(userId);
            List<UserRolesDto> left = this.userRoleRepository.findByListLeft(userId);

            if (right.isEmpty()) {
                List<Role> roles = this.roleService.findAllRoles();

                for (Role r : roles) {
                    UserRolesDto userRolesDto = new UserRolesDto();
                    userRolesDto.setUserId(userId);
                    userRolesDto.setRoleId(r.getId());
                    userRolesDto.setRoleName(r.getName());
                    left.add(userRolesDto);
                }

            }

            transferList.put("left", left);
            transferList.put("right", right);

            return transferList;

        }

    }

}
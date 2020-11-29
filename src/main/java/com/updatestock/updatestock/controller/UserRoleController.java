package com.updatestock.updatestock.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import com.updatestock.updatestock.dto.UserRolesDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;
import com.updatestock.updatestock.service.UserRoleService;
import com.updatestock.updatestock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Cadastrar/Atualizar Permissões do Usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveTransferList(Principal principal,
                                                    @PathVariable(value = "userId") Integer userId,
                                                    @RequestBody Map<String, List<UserRolesDto>> map) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(this.userRoleService.saveTransferList(principal, userId, map), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar Permissão do Usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.DELETE)
    public ResponseEntity<UserRole> delete(@RequestParam(value = "userId", required = true) Integer userId,
                                           @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        this.userRoleService.delete(userRoleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Permissão do Usuário pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole/findById", method = RequestMethod.GET)
    public ResponseEntity<UserRole> findById(@RequestParam(value = "userId", required = true) Integer userId,
                                             @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException, BadRequestException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        return new ResponseEntity<>(this.userRoleService.findById(userRoleId), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todas as permissões do usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    public Page<UserRole> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "5") int size) {
        return this.userRoleService.findAll(page, size);
    }

    @ApiOperation(value = "Buscar todos os usuários")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO') && " +
                          "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/userRole/users", method = RequestMethod.GET)
    public Page<User> findAllUsers(Principal principal,
                                   @RequestParam(value = "offset", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "5") int size) throws NotFoundException {
        return userService.findAllUsers(principal, page, size);
    }

    @ApiOperation(value = "Buscar todas as permissões do usuário pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO') && " +
                           "hasAuthority('ROLE_PESQUISAR_PERMISSAO')")
    @RequestMapping(value = "/userRole/findTransferList/{userId}", method = RequestMethod.GET)
    public Map<String, List<UserRolesDto>> findTransferList(Principal principal, 
                                                            @PathVariable(value = "userId") Integer userId) throws NotFoundException, BadRequestException {
        return this.userRoleService.findTransferList(principal, userId);
    }
    
}
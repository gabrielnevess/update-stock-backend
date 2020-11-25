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

@RestController
@RequestMapping("/api")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveTransferList(@RequestBody Map<String, List<UserRolesDto>> map) throws BadRequestException {
        return new ResponseEntity<>(userRoleService.saveTransferList(map), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.DELETE)
    public ResponseEntity<UserRole> delete(@RequestParam(value = "userId", required = true) Integer userId,
                                           @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        userRoleService.delete(userRoleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole/findById", method = RequestMethod.GET)
    public ResponseEntity<UserRole> findById(Principal principal,
                                             @RequestParam(value = "userId", required = true) Integer userId,
                                             @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException, BadRequestException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        return new ResponseEntity<>(userRoleService.findById(userRoleId, principal), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    public Page<UserRole> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "5") int size) {
        return userRoleService.findAll(page, size);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO') && " +
                          "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/userRole/users", method = RequestMethod.GET)
    public Page<User> findAllUsers(@RequestParam(value = "offset", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "5") int size) {
        return userRoleService.findAllUsers(page, size);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO_PERMISSAO') && " +
                           "hasAuthority('ROLE_PESQUISAR_PERMISSAO')")
    @RequestMapping(value = "/userRole/findTransferList/{userId}", method = RequestMethod.GET)
    public Map<String, List<UserRolesDto>> findTransferList(@PathVariable(value = "userId") Integer userId) {
        return userRoleService.findTransferList(userId);
    }
    
}
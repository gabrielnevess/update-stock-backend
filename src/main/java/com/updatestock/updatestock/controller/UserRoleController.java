package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;
import com.updatestock.updatestock.service.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    public ResponseEntity<UserRole> save(@Valid @RequestBody UserRole userRole) {
        return new ResponseEntity<>(userRoleService.save(userRole), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.PUT)
    public ResponseEntity<UserRole> update(@Valid @RequestBody UserRole userRole) throws NotFoundException {
        return new ResponseEntity<>(userRoleService.update(userRole), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_USUARIO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.DELETE)
    public ResponseEntity<UserRole> delete(@RequestParam(value = "userId", required = true) Integer userId,
                                           @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        userRoleService.delete(userRoleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/userRole/findById", method = RequestMethod.GET)
    public ResponseEntity<UserRole> findById(@RequestParam(value = "userId", required = true) Integer userId,
                                             @RequestParam(value = "roleId", required = true) Integer roleId) throws NotFoundException {
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        userRoleService.delete(userRoleId);
        return new ResponseEntity<>(userRoleService.findById(userRoleId), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    public Page<UserRole> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "5") int size) {
        return userRoleService.findAll(page, size);
    }

}
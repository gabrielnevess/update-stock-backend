package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Role;
import com.updatestock.updatestock.service.RoleService;

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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Cadastrar Permissão")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PERMISSAO')")
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ResponseEntity<Role> save(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(this.roleService.save(role), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Permissão")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PERMISSAO')")
    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public ResponseEntity<Role> update(@Valid @RequestBody Role role) throws NotFoundException {
        return new ResponseEntity<>(this.roleService.update(role), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar Permissão")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_PERMISSAO')")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> delete(@PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        this.roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Permissão pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PERMISSAO')")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ResponseEntity<Role> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.roleService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todas as permissões")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PERMISSAO')")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Page<Role> findAll(@RequestParam(value = "offset", defaultValue="0") int page,
                              @RequestParam(value = "limit", defaultValue="5") int size) {
        return this.roleService.findAll(page, size);
    }

}

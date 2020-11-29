package com.updatestock.updatestock.controller;

import java.security.Principal;
import javax.validation.Valid;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.User;
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
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Cadastrar Usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> save(@Valid @RequestBody User user) throws BadRequestException {
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_USUARIO')")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> update(Principal principal, @Valid @RequestBody User user) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(this.userService.update(user, principal), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar Usuário")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_USUARIO')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(Principal principal, 
    								   @PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        this.userService.delete(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Usuário pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(Principal principal, 
    									 @PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(this.userService.findById(id, principal), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todos os usuários diferente do usuário logado")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_USUARIO')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Page<User> findAll(Principal principal,
                               @RequestParam(value = "offset", required = false) Integer page,
                               @RequestParam(value = "limit", required = false) Integer size) throws NotFoundException {
        return this.userService.findAllUsers(principal, page, size);
    }
    
}
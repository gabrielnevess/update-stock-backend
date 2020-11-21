package com.updatestock.updatestock.controller;

import java.security.Principal;
import javax.validation.Valid;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> save(@Valid @RequestBody User user) throws BadRequestException {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> update(Principal principal, @Valid @RequestBody User user) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(userService.update(user, principal), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(Principal principal, 
    									 @PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(userService.findById(id, principal), HttpStatus.OK);
    }
    
}
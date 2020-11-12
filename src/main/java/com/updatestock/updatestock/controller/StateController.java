package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.State;
import com.updatestock.updatestock.service.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StateController {

    @Autowired
    private StateService stateService;

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public ResponseEntity<State> save(@Valid @RequestBody State state) {
        return new ResponseEntity<>(stateService.save(state), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/state", method = RequestMethod.PUT)
    public ResponseEntity<State> update(@Valid @RequestBody State state) throws NotFoundException {
        return new ResponseEntity<>(stateService.update(state), HttpStatus.OK);
    }

    @RequestMapping(value = "/state/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<State> delete(@PathVariable(value = "id") Integer id) throws NotFoundException {
        stateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/state/{id}", method = RequestMethod.GET)
    public ResponseEntity<State> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(stateService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public Page<State> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                               @RequestParam(value = "limit", defaultValue = "10") int size) {
        return stateService.findAll(page, size);
    }

}

package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.ProductInput;
import com.updatestock.updatestock.service.ProductInputService;

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
public class ProductInputController {

    @Autowired
    private ProductInputService productInputService;

    @RequestMapping(value = "/productInput", method = RequestMethod.POST)
    public ResponseEntity<ProductInput> save(@Valid @RequestBody ProductInput productInput) throws NotFoundException {
        return new ResponseEntity<>(productInputService.save(productInput), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/productInput", method = RequestMethod.PUT)
    public ResponseEntity<ProductInput> update(@Valid @RequestBody ProductInput productInput) throws NotFoundException {
        return new ResponseEntity<>(productInputService.update(productInput), HttpStatus.OK);
    }

    @RequestMapping(value = "/productInput/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductInput> delete(@PathVariable(value = "id") Integer id) throws NotFoundException {
        productInputService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/productInput/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductInput> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(productInputService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/productInput", method = RequestMethod.GET)
    public Page<ProductInput> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                               @RequestParam(value = "limit", defaultValue = "10") int size) {
        return productInputService.findAll(page, size);
    }

}

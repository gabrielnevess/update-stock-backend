package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.service.ProductService;

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
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) throws NotFoundException {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@Valid @RequestBody Product product) throws NotFoundException {
        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_PRODUTO')")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> delete(@PathVariable(value = "id") Integer id) throws NotFoundException {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PRODUTO')")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Page<Product> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                                 @RequestParam(value = "limit", defaultValue = "10") int size) {
        return productService.findAll(page, size);
    }

}

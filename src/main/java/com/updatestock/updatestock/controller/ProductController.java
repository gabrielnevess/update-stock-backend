package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.BadRequestException;
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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Cadastrar Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) throws NotFoundException {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@Valid @RequestBody Product product) throws NotFoundException {
        return new ResponseEntity<>(this.productService.update(product), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_PRODUTO')")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> delete(@PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        this.productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Produto pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PRODUTO')")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todos os produtos")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PRODUTO')")
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Page<Product> findAll(@RequestParam(value = "offset", required = false) int page,
                                 @RequestParam(value = "limit", required = false) int size) {
        return this.productService.findAll(page, size);
    }

}

package com.updatestock.updatestock.controller;

import java.security.Principal;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.ProductOutput;
import com.updatestock.updatestock.service.ProductOutputService;

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
public class ProductOutputController {

    @Autowired
    private ProductOutputService productOutputService;

    @ApiOperation(value = "Cadastrar Saída de Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_SAIDA_PRODUTO')")
    @RequestMapping(value = "/productOutput", method = RequestMethod.POST)
    public ResponseEntity<ProductOutput> save(Principal principal,
                                              @Valid @RequestBody ProductOutput productOutput) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(productOutputService.save(principal, productOutput), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Buscar Saída de Produto pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_SAIDA_PRODUTO')")
    @RequestMapping(value = "/productOutput/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductOutput> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(productOutputService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todas as saídas de produtos")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_SAIDA_PRODUTO')")
    @RequestMapping(value = "/productOutput", method = RequestMethod.GET)
    public Page<ProductOutput> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                               @RequestParam(value = "limit", defaultValue = "5") int size) {
        return productOutputService.findAll(page, size);
    }

}

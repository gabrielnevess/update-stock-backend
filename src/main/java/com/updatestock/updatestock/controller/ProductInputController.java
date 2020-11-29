package com.updatestock.updatestock.controller;

import java.security.Principal;

import javax.validation.Valid;

import com.updatestock.updatestock.dto.IMonthlyQtdProductInputDto;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.ProductInput;
import com.updatestock.updatestock.service.ProductInputService;

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
public class ProductInputController {

    @Autowired
    private ProductInputService productInputService;

    @ApiOperation(value = "Cadastrar Entrada de Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_ENTRADA_PRODUTO')")
    @RequestMapping(value = "/productInput", method = RequestMethod.POST)
    public ResponseEntity<ProductInput> save(Principal principal,
                                             @Valid @RequestBody ProductInput productInput) throws NotFoundException {
        return new ResponseEntity<>(this.productInputService.save(principal, productInput), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Buscar Entrada de Produto pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ENTRADA_PRODUTO')")
    @RequestMapping(value = "/productInput/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductInput> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.productInputService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todas as entradas de produtos")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ENTRADA_PRODUTO')")
    @RequestMapping(value = "/productInput", method = RequestMethod.GET)
    public Page<ProductInput> findAll(@RequestParam(value = "offset", required = false) Integer page,
                                      @RequestParam(value = "limit", required = false) Integer size) {
        return this.productInputService.findAll(page, size);
    }

    @ApiOperation(value = "Buscar a quantidade de entrada de produtos feitas no mês atual")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ENTRADA_PRODUTO')")
    @RequestMapping(value = "/productInput/monthlyQtdProductInput", method = RequestMethod.GET)
    public ResponseEntity<IMonthlyQtdProductInputDto> monthlyQtdProductInput() throws NotFoundException {
        return new ResponseEntity<>(this.productInputService.monthlyQtdProductInput(), HttpStatus.OK);
    }

}

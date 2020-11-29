package com.updatestock.updatestock.controller;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Stock;
import com.updatestock.updatestock.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Deletar Estoque")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_ESTOQUE')")
    @RequestMapping(value = "/stock/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Stock> delete(@PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        this.stockService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Estoque pelo Id do Produto")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ESTOQUE')")
    @RequestMapping(value = "/stock/productId/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stock> findByProductId(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.stockService.findByProductId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Estoque pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ESTOQUE')")
    @RequestMapping(value = "/stock/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stock> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.stockService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todos os estoques")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_ESTOQUE')")
    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public Page<Stock> findAll(@RequestParam(value = "offset", required = false) Integer page,
                               @RequestParam(value = "limit", required = false) Integer size) {
        return this.stockService.findAll(page, size);
    }

}
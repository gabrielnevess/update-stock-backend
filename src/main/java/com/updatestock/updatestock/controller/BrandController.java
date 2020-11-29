package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.service.BrandService;

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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "Cadastrar Marca")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_MARCA')")
    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public ResponseEntity<Brand> save(@Valid @RequestBody Brand brand) {
        return new ResponseEntity<>(this.brandService.save(brand), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atulizar Marca")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_MARCA')")
    @RequestMapping(value = "/brand", method = RequestMethod.PUT)
    public ResponseEntity<Brand> update(@Valid @RequestBody Brand brand) throws NotFoundException {
        return new ResponseEntity<>(this.brandService.update(brand), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar Marca")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_MARCA')")
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Brand> delete(@PathVariable(value = "id") Integer id) throws NotFoundException, BadRequestException {
        this.brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Pesquisar Marca pelo Id")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_MARCA')")
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.GET)
    public ResponseEntity<Brand> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(this.brandService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todas as marcas")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_MARCA')")
    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public Page<Brand> findAll(@RequestParam(value = "offset", required = false) Integer page,
                               @RequestParam(value = "limit", required = false) Integer size) {
        return this.brandService.findAll(page, size);
    }

}

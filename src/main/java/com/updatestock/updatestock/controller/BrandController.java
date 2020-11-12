package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.service.BrandService;

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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public ResponseEntity<Brand> save(@Valid @RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.save(brand), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/brand", method = RequestMethod.PUT)
    public ResponseEntity<Brand> update(@Valid @RequestBody Brand brand) throws NotFoundException {
        return new ResponseEntity<>(brandService.update(brand), HttpStatus.OK);
    }

    @RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Brand> delete(@PathVariable(value = "id") Integer id) throws NotFoundException {
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/brand/{id}", method = RequestMethod.GET)
    public ResponseEntity<Brand> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public Page<Brand> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                               @RequestParam(value = "limit", defaultValue = "10") int size) {
        return brandService.findAll(page, size);
    }

}

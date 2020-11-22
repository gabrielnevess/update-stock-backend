package com.updatestock.updatestock.controller;

import javax.validation.Valid;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.MeasurementUnit;
import com.updatestock.updatestock.service.MeasurementUnitService;

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
public class MeasurementUnitController {

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_UNIDADE_MEDIDA')")
    @RequestMapping(value = "/measurementUnit", method = RequestMethod.POST)
    public ResponseEntity<MeasurementUnit> save(@Valid @RequestBody MeasurementUnit measurementUnit) {
        return new ResponseEntity<>(measurementUnitService.save(measurementUnit), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_UNIDADE_MEDIDA')")
    @RequestMapping(value = "/measurementUnit", method = RequestMethod.PUT)
    public ResponseEntity<MeasurementUnit> update(@Valid @RequestBody MeasurementUnit measurementUnit) throws NotFoundException {
        return new ResponseEntity<>(measurementUnitService.update(measurementUnit), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_UNIDADE_MEDIDA')")
    @RequestMapping(value = "/measurementUnit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MeasurementUnit> delete(@PathVariable(value = "id") Integer id) throws NotFoundException {
        measurementUnitService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_UNIDADE_MEDIDA')")
    @RequestMapping(value = "/measurementUnit/{id}", method = RequestMethod.GET)
    public ResponseEntity<MeasurementUnit> findById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(measurementUnitService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_UNIDADE_MEDIDA')")
    @RequestMapping(value = "/measurementUnit", method = RequestMethod.GET)
    public Page<MeasurementUnit> findAll(@RequestParam(value = "offset", defaultValue = "0") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int size) {
        return measurementUnitService.findAll(page, size);
    }

}

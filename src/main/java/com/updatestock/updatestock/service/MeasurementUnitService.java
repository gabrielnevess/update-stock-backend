package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.MeasurementUnit;
import com.updatestock.updatestock.repository.MeasurementUnitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MeasurementUnitService {
    
    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    public MeasurementUnit save(MeasurementUnit mu) {
        return this.measurementUnitRepository.save(mu);
    }

    public MeasurementUnit update(MeasurementUnit mu) throws NotFoundException {
        MeasurementUnit measurementUnit = this.measurementUnitRepository.findById(mu.getId())
                          .orElseThrow(() -> new NotFoundException("Unidade de medida não encontrada com o id :: " + mu.getId()));
        measurementUnit.setName(mu.getName());
        measurementUnit.setPrefix(mu.getPrefix());
        return this.measurementUnitRepository.save(measurementUnit);
    }

    public void delete(MeasurementUnit mu) throws NotFoundException {
        MeasurementUnit measurementUnit = this.measurementUnitRepository.findById(mu.getId())
                          .orElseThrow(() -> new NotFoundException("Unidade de medida não encontrada com o id :: " + mu.getId()));
        this.measurementUnitRepository.delete(measurementUnit);
    }

    public MeasurementUnit findById(Integer id) throws NotFoundException {
        return this.measurementUnitRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Unidade de medida não encontrada com o id :: " + id));
    }

    public Page<MeasurementUnit> findAll(int page, int size) throws NotFoundException {
        return this.measurementUnitRepository.findAll(PageRequest.of(page, size));
    }

}

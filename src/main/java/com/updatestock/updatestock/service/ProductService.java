package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.model.MeasurementUnit;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.State;
import com.updatestock.updatestock.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private StateService stateService;

    public Product save(Product p) {
        return this.productRepository.save(p);
    }

    public Product update(Product p) throws NotFoundException {
        Product product = this.productRepository.findById(p.getId())
                          .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + p.getId()));
        
        Brand brand = this.brandService.findById(p.getBrandId());
        MeasurementUnit measurementUnit = this.measurementUnitService.findById(p.getMeasurementUnitId());
        State state = this.stateService.findById(product.getStateId());

        product.setBrandId(brand.getId());
        product.setMeasurementUnitId(measurementUnit.getId());
        product.setStateId(state.getId());
        product.setName(p.getName());
        product.setModel(p.getModel());
        product.setSerial(product.getSerial());
        return this.productRepository.save(product);
    }

    public void delete(Product p) throws NotFoundException {
        Product product = this.productRepository.findById(p.getId())
                          .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + p.getId()));
        this.productRepository.delete(product);
    }

    public Product findById(Integer id) throws NotFoundException {
        return this.productRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + id));
    }

    public Page<Product> findAll(int page, int size) throws NotFoundException {
        return this.productRepository.findAll(PageRequest.of(page, size));
    }

}

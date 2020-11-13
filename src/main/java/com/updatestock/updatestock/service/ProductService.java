package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.model.MeasurementUnit;
import com.updatestock.updatestock.model.Product;
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

    public Product save(Product p) throws NotFoundException {
        Brand brand = this.brandService.findById(p.getBrandId());
        MeasurementUnit measurementUnit = this.measurementUnitService.findById(p.getMeasurementUnitId());

        p.setBrandId(brand.getId());
        p.setMeasurementUnitId(measurementUnit.getId());
        return this.productRepository.save(p);
    }

    public Product update(Product p) throws NotFoundException {
        Product product = this.productRepository.findById(p.getId())
                          .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + p.getId()));
        
        Brand brand = this.brandService.findById(p.getBrandId());
        MeasurementUnit measurementUnit = this.measurementUnitService.findById(p.getMeasurementUnitId());

        product.setBrandId(brand.getId());
        product.setMeasurementUnitId(measurementUnit.getId());
        product.setName(p.getName());
        product.setModel(p.getModel());
        product.setSerial(p.getSerial());
        return this.productRepository.save(product);
    }

    public void delete(Integer id) throws NotFoundException {
        Product product = this.productRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + id));
        this.productRepository.delete(product);
    }

    public Product findById(Integer id) throws NotFoundException {
        return this.productRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Produto não encontrada com o id :: " + id));
    }

    public Page<Product> findAll(int page, int size) {
        return this.productRepository.findAll(PageRequest.of(page, size));
    }

}

package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.model.MeasurementUnit;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.Stock;
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
    private StockService stockService;

    public Product save(Product p) throws NotFoundException {
        Brand brand = this.brandService.findById(p.getBrand().getId());
        MeasurementUnit measurementUnit = this.measurementUnitService.findById(p.getMeasurementUnit().getId());

        p.setBrand(brand);
        p.setMeasurementUnit(measurementUnit);
        return this.productRepository.save(p);
    }

    public Product update(Product p) throws NotFoundException {
        Product product = this.productRepository.findById(p.getId())
                          .orElseThrow(() -> new NotFoundException(String.format("Produto não encontrada com o id :: %d", p.getId())));
        
        Brand brand = this.brandService.findById(p.getBrand().getId());
        MeasurementUnit measurementUnit = this.measurementUnitService.findById(p.getMeasurementUnit().getId());

        product.setBrand(brand);
        product.setMeasurementUnit(measurementUnit);
        product.setName(p.getName());
        product.setModel(p.getModel());
        product.setSerial(p.getSerial());
        return this.productRepository.save(product);
    }

    public void delete(Integer id) throws NotFoundException, BadRequestException {
        Product product = this.productRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Produto não encontrada com o id :: %d", id)));
        try {
            this.productRepository.delete(product);
        } catch (Exception ex) {
            throw new BadRequestException(String.format("O produto com o id :: %d não pode ser deletado, pois está em uso!", id));
        }
    }

    public Product findById(Integer id) throws NotFoundException {
        return this.productRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Produto não encontrada com o id :: %d", id)));
    }

    public Page<Product> findAll(int page, int size) {
        return this.productRepository.findAll(PageRequest.of(page, size));
    }

    public Product findByIdWithStock(Integer id) throws NotFoundException, BadRequestException {
        Product product = this.findById(id);
        Stock stock = this.stockService.findByProductId(product.getId());
        if(stock.getQtd() > 0) {
            return product;   
        } else {
            throw new BadRequestException(String.format("Sem estoque para o produto %s!", product.getName()));
        }
    }

}

package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.ProductOutput;
import com.updatestock.updatestock.repository.ProductOutputRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductOutputService {
    
    @Autowired
    private ProductOutputRepository productOutputRepository;

    @Autowired
    private ProductService productService;

    public ProductOutput save(ProductOutput po) throws NotFoundException {
        Product product = this.productService.findById(po.getProduct().getId());
        po.setProduct(product);
        return this.productOutputRepository.save(po);
    }

    public ProductOutput update(ProductOutput po) throws NotFoundException {
        ProductOutput productOutput = this.productOutputRepository.findById(po.getId())
                          .orElseThrow(() -> new NotFoundException("Saída de Produto não encontrado com o id :: " + po.getId()));
        
        Product product = this.productService.findById(po.getProduct().getId());
        
        productOutput.setProduct(product);
        productOutput.setQtd(po.getQtd());
        productOutput.setObservation(po.getObservation());
        return this.productOutputRepository.save(productOutput);
    }

    public void delete(Integer id) throws NotFoundException {
        ProductOutput productOutput = this.productOutputRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException("Saída de Produto não encontrado com o id :: " + id));
        this.productOutputRepository.delete(productOutput);
    }

    public ProductOutput findById(Integer id) throws NotFoundException {
        return this.productOutputRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Saída de Produto não encontrado com o id :: " + id));
    }

    public Page<ProductOutput> findAll(int page, int size) {
        return this.productOutputRepository.findAll(PageRequest.of(page, size));
    }

}

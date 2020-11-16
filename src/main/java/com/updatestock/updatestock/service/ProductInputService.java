package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.ProductInput;
import com.updatestock.updatestock.repository.ProductInputRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductInputService {
    
    @Autowired
    private ProductInputRepository productInputRepository;

    @Autowired
    private ProductService productService;

    public ProductInput save(ProductInput pi) throws NotFoundException {
        Product product = this.productService.findById(pi.getProduct().getId());
        pi.setProduct(product);
        return this.productInputRepository.save(pi);
    }

    public ProductInput update(ProductInput pi) throws NotFoundException {
        ProductInput productInput = this.productInputRepository.findById(pi.getId())
                          .orElseThrow(() -> new NotFoundException("Entrada de Produto não encontrado com o id :: " + pi.getId()));

        Product product = this.productService.findById(pi.getProduct().getId());

        productInput.setProduct(product);
        productInput.setQtd(pi.getQtd());
        productInput.setObservation(pi.getObservation());
        return this.productInputRepository.save(productInput);
    }

    public void delete(Integer id) throws NotFoundException {
        ProductInput productInput = this.productInputRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException("Entrada de Produto não encontrado com o id :: " + id));
        this.productInputRepository.delete(productInput);
    }

    public ProductInput findById(Integer id) throws NotFoundException {
        return this.productInputRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Entrada de Produto não encontrado com o id :: " + id));
    }

    public Page<ProductInput> findAll(int page, int size) {
        return this.productInputRepository.findAll(PageRequest.of(page, size));
    }

}

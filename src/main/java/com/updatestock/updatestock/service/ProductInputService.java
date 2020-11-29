package com.updatestock.updatestock.service;

import java.security.Principal;
import java.sql.Timestamp;

import com.updatestock.updatestock.dto.IMonthlyQtdProductInputDto;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.ProductInput;
import com.updatestock.updatestock.model.User;
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

    @Autowired
    private UserService userService;

    public ProductInput save(Principal principal, ProductInput pi) throws NotFoundException {
        User user = this.userService.findById(principal);
        Product product = this.productService.findById(pi.getProduct().getId());
        
        pi.setUser(user);
        pi.setProduct(product);
        return this.productInputRepository.save(pi);
    }

    public ProductInput update(Principal principal, ProductInput pi) throws NotFoundException {
        User user = this.userService.findById(principal);
        ProductInput productInput = this.productInputRepository.findById(pi.getId())
                                        .orElseThrow(() -> new NotFoundException(String.format("Entrada de Produto não encontrado com o id :: %d", pi.getId())));
        Product product = this.productService.findById(pi.getProduct().getId());
        
        productInput.setUser(user);
        productInput.setProduct(product);
        productInput.setQtd(pi.getQtd());
        productInput.setObservation(pi.getObservation());
        return this.productInputRepository.save(productInput);
    }

    public void delete(Integer id) throws NotFoundException {
        ProductInput productInput = this.productInputRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Entrada de Produto não encontrado com o id :: %d", id)));
        this.productInputRepository.delete(productInput);
    }

    public ProductInput findById(Integer id) throws NotFoundException {
        return this.productInputRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Entrada de Produto não encontrado com o id :: %d", id)));
    }

    public Page<ProductInput> findAll(Integer page, Integer size) {
        return this.productInputRepository.findAll(PageRequest.of(page, size));
    }

    public IMonthlyQtdProductInputDto monthlyQtdProductInput() {
		return this.productInputRepository.monthlyQtdProductInput(new Timestamp(System.currentTimeMillis()));
	}

}

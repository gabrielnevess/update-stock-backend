package com.updatestock.updatestock.service;

import java.security.Principal;
import java.sql.Timestamp;

import com.updatestock.updatestock.dto.IMonthlyQtdProductOutputDto;
import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.ProductOutput;
import com.updatestock.updatestock.model.Stock;
import com.updatestock.updatestock.model.User;
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

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    public ProductOutput save(Principal principal, ProductOutput po) throws NotFoundException, BadRequestException {
        User user = this.userService.findById(principal);
        Product product = this.productService.findById(po.getProduct().getId());
        
        Stock stock = stockService.findByProductId(product.getId());
        Integer qtdStock = (stock.getQtd() - po.getQtd());
        if(qtdStock < 0) {
            throw new BadRequestException("Quantidade do estoque excedida!");
        }

        po.setUser(user);
        po.setProduct(product);
        return this.productOutputRepository.save(po);
    }

    public ProductOutput update(Principal principal, ProductOutput po) throws NotFoundException {
        User user = this.userService.findById(principal);
        ProductOutput productOutput = this.productOutputRepository.findById(po.getId())
                          .orElseThrow(() -> new NotFoundException(String.format("Saída de Produto não encontrado com o id :: %d", po.getId())));
        
        Product product = this.productService.findById(po.getProduct().getId());
        
        productOutput.setUser(user);
        productOutput.setProduct(product);
        productOutput.setQtd(po.getQtd());
        productOutput.setObservation(po.getObservation());
        return this.productOutputRepository.save(productOutput);
    }

    public void delete(Integer id) throws NotFoundException {
        ProductOutput productOutput = this.productOutputRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Saída de Produto não encontrado com o id :: %d", id)));
        this.productOutputRepository.delete(productOutput);
    }

    public ProductOutput findById(Integer id) throws NotFoundException {
        return this.productOutputRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Saída de Produto não encontrado com o id :: %d", id)));
    }

    public Page<ProductOutput> findAll(Integer page, Integer size) {
        return this.productOutputRepository.findAll(PageRequest.of(page, size));
    }

    public IMonthlyQtdProductOutputDto monthlyQtdProductOutput() {
		return this.productOutputRepository.monthlyQtdProductOutput(new Timestamp(System.currentTimeMillis()));
	}

}

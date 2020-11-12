package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Product;
import com.updatestock.updatestock.model.Stock;
import com.updatestock.updatestock.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    public Stock save(Stock st) throws NotFoundException {
        Product product = this.productService.findById(st.getProductId());
        st.setProductId(product.getId());
        return this.stockRepository.save(st);
    }

    public Stock update(Stock st) throws NotFoundException {
        Stock stock = this.stockRepository.findById(st.getId())
                          .orElseThrow(() -> new NotFoundException("Estoque não encontrado com o id :: " + st.getId()));

        Product product = this.productService.findById(st.getProductId());

        stock.setProductId(product.getId());
        stock.setQtd(st.getQtd());
        return this.stockRepository.save(stock);
    }

    public void delete(Integer id) throws NotFoundException {
        Stock stock = this.stockRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException("Estoque não encontrado com o id :: " + id));
        this.stockRepository.delete(stock);
    }

    public Stock findById(Integer id) throws NotFoundException {
        return this.stockRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Estoque não encontrado com o id :: " + id));
    }

    public Page<Stock> findAll(int page, int size) {
        return this.stockRepository.findAll(PageRequest.of(page, size));
    }

}

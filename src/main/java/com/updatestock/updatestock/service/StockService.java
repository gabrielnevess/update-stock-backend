package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.BadRequestException;
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
        Product product = this.productService.findById(st.getProduct().getId());
        st.setProduct(product);
        return this.stockRepository.save(st);
    }

    public Stock update(Stock st) throws NotFoundException {
        Stock stock = this.stockRepository.findById(st.getId())
                          .orElseThrow(() -> new NotFoundException(String.format("Estoque não encontrado com o id :: %d", st.getId())));

        Product product = this.productService.findById(st.getProduct().getId());

        stock.setProduct(product);
        stock.setQtd(st.getQtd());
        return this.stockRepository.save(stock);
    }

    public void delete(Integer id) throws NotFoundException, BadRequestException {
        Stock stock = this.stockRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Estoque não encontrado com o id :: %d", id)));
        try {
            this.stockRepository.delete(stock);
        } catch (Exception ex) {
            throw new BadRequestException(
                    String.format("O Estoque com o id :: %d não pôde ser deletada, pois está em uso!", id));
        }
    }

    public Stock findById(Integer id) throws NotFoundException {
        return this.stockRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Estoque não encontrado com o id :: %d", id)));
    }

    public Stock findByProductId(Integer productId) throws NotFoundException {
        return this.stockRepository.findByProduct_Id(productId)
                   .orElseThrow(() -> new NotFoundException(String.format("Estoque não encontrado com o id do produto :: %d", productId)));
    }

    public Page<Stock> findAll(Integer page, Integer size) {
        return this.stockRepository.findAll(PageRequest.of(page, size));
    }

}

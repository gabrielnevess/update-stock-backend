package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.BadRequestException;
import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.Brand;
import com.updatestock.updatestock.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BrandService {
    
    @Autowired
    private BrandRepository brandRepository;

    public Brand save(Brand b) {
        return this.brandRepository.save(b);
    }

    public Brand update(Brand b) throws NotFoundException {
        Brand brand = this.brandRepository.findById(b.getId())
                          .orElseThrow(() -> new NotFoundException(String.format("Marca não encontrada com o id :: %d", b.getId())));
        brand.setName(b.getName());
        return this.brandRepository.save(brand);
    }

    public void delete(Integer id) throws NotFoundException, BadRequestException {
        Brand brand = this.brandRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException(String.format("Marca não encontrada com o id :: %d", id)));
        try {
            this.brandRepository.delete(brand);
        } catch (Exception ex) {
            throw new BadRequestException(
                    String.format("A Marca com o id :: %d não pôde ser deletada, pois está em uso!", id));
        }
    }

    public Brand findById(Integer id) throws NotFoundException {
        return this.brandRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException(String.format("Marca não encontrada com o id :: %d", id)));
    }

    public Page<Brand> findAll(int page, int size) {
        return this.brandRepository.findAll(PageRequest.of(page, size));
    }

}

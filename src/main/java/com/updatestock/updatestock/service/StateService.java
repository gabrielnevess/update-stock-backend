package com.updatestock.updatestock.service;

import com.updatestock.updatestock.exception.NotFoundException;
import com.updatestock.updatestock.model.State;
import com.updatestock.updatestock.repository.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    
    @Autowired
    private StateRepository stateRepository;

    public State save(State st) {
        return this.stateRepository.save(st);
    }

    public State update(State st) throws NotFoundException {
        State state = this.stateRepository.findById(st.getId())
                          .orElseThrow(() -> new NotFoundException("Estado do produto não encontrado com o id :: " + st.getId()));
        state.setName(st.getName());
        return this.stateRepository.save(state);
    }

    public void delete(Integer id) throws NotFoundException {
        State state = this.stateRepository.findById(id)
                          .orElseThrow(() -> new NotFoundException("Estado do produto não encontrado com o id :: " + id));
        this.stateRepository.delete(state);
    }

    public State findById(Integer id) throws NotFoundException {
        return this.stateRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("Estado do produto não encontrado com o id :: " + id));
    }

    public Page<State> findAll(int page, int size) {
        return this.stateRepository.findAll(PageRequest.of(page, size));
    }

}

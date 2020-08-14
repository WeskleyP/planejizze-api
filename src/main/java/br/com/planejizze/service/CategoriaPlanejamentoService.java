package br.com.planejizze.service;

import br.com.planejizze.model.CategoriaPlanejamento;
import br.com.planejizze.repository.CategoriaPlanejamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaPlanejamentoService extends AbstractService<CategoriaPlanejamento, Long, CategoriaPlanejamentoRepository> {
    @Autowired
    public CategoriaPlanejamentoService(CategoriaPlanejamentoRepository categoriaPlanejamentoRepository) {
        super(categoriaPlanejamentoRepository);
    }
}

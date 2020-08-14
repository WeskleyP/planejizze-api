package br.com.planejizze.service;

import br.com.planejizze.model.Planejamento;
import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanejamentoService extends AbstractService<Planejamento, Long, PlanejamentoRepository> {
    @Autowired
    public PlanejamentoService(PlanejamentoRepository planejamentoRepository) {
        super(planejamentoRepository);
    }
}

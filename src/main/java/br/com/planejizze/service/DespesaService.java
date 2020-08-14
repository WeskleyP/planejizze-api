package br.com.planejizze.service;

import br.com.planejizze.model.Despesa;
import br.com.planejizze.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DespesaService extends AbstractService<Despesa, Long, DespesaRepository> {
    @Autowired
    public DespesaService(DespesaRepository despesaRepository) {
        super(despesaRepository);
    }
}

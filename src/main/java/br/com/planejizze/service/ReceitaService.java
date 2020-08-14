package br.com.planejizze.service;

import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceitaService extends AbstractService<Receita, Long, ReceitaRepository> {
    @Autowired
    public ReceitaService(ReceitaRepository receitaRepository) {
        super(receitaRepository);
    }
}

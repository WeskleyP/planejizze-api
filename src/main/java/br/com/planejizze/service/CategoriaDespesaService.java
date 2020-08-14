package br.com.planejizze.service;

import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaDespesaService extends AbstractService<CategoriaDespesa, Long, CategoriaDespesaRepository> {
    @Autowired
    public CategoriaDespesaService(CategoriaDespesaRepository categoriaDespesaRepository) {
        super(categoriaDespesaRepository);
    }
}

package br.com.planejizze.service;

import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaReceitaService extends AbstractService<CategoriaReceita, Long, CategoriaReceitaRepository> {
    @Autowired
    public CategoriaReceitaService(CategoriaReceitaRepository categoriaReceitaRepository) {
        super(categoriaReceitaRepository);
    }
}

package br.com.planejizze.resource;

import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import br.com.planejizze.service.CategoriaReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/despesa")
public class DespesaResource extends AbstractResource<CategoriaReceita, Long, CategoriaReceitaRepository, CategoriaReceitaService> {
    private final CategoriaReceitaService categoriaReceitaService;

    @Autowired
    protected DespesaResource(CategoriaReceitaService service) {
        super(service);
        this.categoriaReceitaService = service;
    }
}

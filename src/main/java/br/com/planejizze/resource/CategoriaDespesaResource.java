package br.com.planejizze.resource;

import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import br.com.planejizze.service.CategoriaDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categoriaDespesa")
public class CategoriaDespesaResource extends AbstractResource<CategoriaDespesa, Long, CategoriaDespesaRepository, CategoriaDespesaService> {
    private final CategoriaDespesaService categoriaDespesaService;

    @Autowired
    protected CategoriaDespesaResource(CategoriaDespesaService service) {
        super(service);
        this.categoriaDespesaService = service;
    }
}

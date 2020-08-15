package br.com.planejizze.resource;

import br.com.planejizze.model.CategoriaPlanejamento;
import br.com.planejizze.repository.CategoriaPlanejamentoRepository;
import br.com.planejizze.service.CategoriaPlanejamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categoriaPlanejamento")
public class CategoriaPlanejamentoResource extends AbstractResource<CategoriaPlanejamento, Long, CategoriaPlanejamentoRepository, CategoriaPlanejamentoService> {
    private final CategoriaPlanejamentoService categoriaPlanejamentoService;

    @Autowired
    protected CategoriaPlanejamentoResource(CategoriaPlanejamentoService service) {
        super(service);
        this.categoriaPlanejamentoService = service;
    }
}

package br.com.planejizze.resource;

import br.com.planejizze.model.Planejamento;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.service.PlanejamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/planejamento")
public class PlanejamentoResource extends AbstractResource<Planejamento, Long, PlanejamentoRepository, PlanejamentoService> {
    private final PlanejamentoService planejamentoService;

    @Autowired
    protected PlanejamentoResource(PlanejamentoService service) {
        super(service);
        this.planejamentoService = service;
    }
}

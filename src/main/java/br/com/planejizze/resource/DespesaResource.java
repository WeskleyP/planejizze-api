package br.com.planejizze.resource;

import br.com.planejizze.model.Despesa;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.service.DespesaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Despesa")
@RestController
@RequestMapping(path = "/despesa")
public class DespesaResource extends AbstractResource<Despesa, Long, DespesaRepository, DespesaService> {
    private final DespesaService despesaService;

    @Autowired
    protected DespesaResource(DespesaService service) {
        super(service);
        this.despesaService = service;
    }
}

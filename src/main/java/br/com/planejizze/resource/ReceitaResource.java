package br.com.planejizze.resource;

import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.service.ReceitaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Receita")
@RestController
@RequestMapping(path = "/receita")
public class ReceitaResource extends AbstractResource<Receita, Long, ReceitaRepository, ReceitaService> {
    private final ReceitaService receitaService;

    @Autowired
    protected ReceitaResource(ReceitaService service) {
        super(service);
        this.receitaService = service;
    }
}

package br.com.planejizze.resource;

import br.com.planejizze.model.Banco;
import br.com.planejizze.repository.BancoRepository;
import br.com.planejizze.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/banco")
public class BancoResource extends AbstractResource<Banco, Long, BancoRepository, BancoService> {
    private final BancoService bancoService;
    @Autowired
    protected BancoResource(BancoService service) {
        super(service);
        this.bancoService = service;
    }
}

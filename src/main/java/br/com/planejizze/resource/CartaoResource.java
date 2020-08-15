package br.com.planejizze.resource;

import br.com.planejizze.model.Cartao;
import br.com.planejizze.repository.CartaoRepository;
import br.com.planejizze.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cartao")
public class CartaoResource extends AbstractResource<Cartao, Long, CartaoRepository, CartaoService> {
    private final CartaoService cartaoService;

    @Autowired
    protected CartaoResource(CartaoService service) {
        super(service);
        this.cartaoService = service;
    }
}

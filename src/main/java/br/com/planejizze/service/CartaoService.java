package br.com.planejizze.service;

import br.com.planejizze.model.Cartao;
import br.com.planejizze.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService extends AbstractService<Cartao, Long, CartaoRepository> {
    @Autowired
    public CartaoService(CartaoRepository cartaoRepository) {
        super(cartaoRepository);
    }
}

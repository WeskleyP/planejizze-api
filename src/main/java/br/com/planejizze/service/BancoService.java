package br.com.planejizze.service;

import br.com.planejizze.model.Banco;
import br.com.planejizze.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancoService extends AbstractService<Banco, Long, BancoRepository> {
    @Autowired
    public BancoService(BancoRepository bancoRepository) {
        super(bancoRepository);
    }
}

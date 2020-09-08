package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Banco;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.BancoRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BancoService extends AbstractService<Banco, Long, BancoRepository> {
    @Autowired
    public BancoService(BancoRepository bancoRepository) {
        super(bancoRepository);
    }

    @Override
    public Banco save(Banco entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public Banco update(Banco entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public List<Banco> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<Banco> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public Banco findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }
}

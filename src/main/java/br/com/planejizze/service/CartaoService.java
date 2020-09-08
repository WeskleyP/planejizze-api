package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Cartao;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.CartaoRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CartaoService extends AbstractService<Cartao, Long, CartaoRepository> {
    @Autowired
    public CartaoService(CartaoRepository cartaoRepository) {
        super(cartaoRepository);
    }

    @Override
    public Cartao save(Cartao entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public Cartao update(Cartao entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public List<Cartao> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<Cartao> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public Cartao findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }
}

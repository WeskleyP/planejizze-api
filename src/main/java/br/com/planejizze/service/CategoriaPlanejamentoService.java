package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaPlanejamento;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.CategoriaPlanejamentoRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CategoriaPlanejamentoService extends AbstractService<CategoriaPlanejamento, Long, CategoriaPlanejamentoRepository> {
    @Autowired
    public CategoriaPlanejamentoService(CategoriaPlanejamentoRepository categoriaPlanejamentoRepository) {
        super(categoriaPlanejamentoRepository);
    }

    @Override
    public CategoriaPlanejamento save(CategoriaPlanejamento entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public CategoriaPlanejamento update(CategoriaPlanejamento entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public List<CategoriaPlanejamento> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<CategoriaPlanejamento> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public CategoriaPlanejamento findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId());
    }

}

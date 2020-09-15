package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Despesa;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DespesaService extends AbstractService<Despesa, Long, DespesaRepository> {
    @Autowired
    public DespesaService(DespesaRepository despesaRepository) {
        super(despesaRepository);
    }

    @Override
    public Despesa save(Despesa entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoPagamento().setDespesa(entity);
        return repo.save(entity);
    }

    @Override
    public Despesa update(Despesa entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoPagamento().setDespesa(entity);
        return repo.save(entity);
    }

    @Override
    public List<Despesa> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<Despesa> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public Despesa findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }
}

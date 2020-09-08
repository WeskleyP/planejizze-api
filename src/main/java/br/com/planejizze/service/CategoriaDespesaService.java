package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CategoriaDespesaService extends AbstractService<CategoriaDespesa, Long, CategoriaDespesaRepository> {
    @Autowired
    public CategoriaDespesaService(CategoriaDespesaRepository categoriaDespesaRepository) {
        super(categoriaDespesaRepository);
    }

    @Override
    public CategoriaDespesa save(CategoriaDespesa entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public CategoriaDespesa update(CategoriaDespesa entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        return repo.save(entity);
    }

    @Override
    public List<CategoriaDespesa> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<CategoriaDespesa> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public CategoriaDespesa findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }

}

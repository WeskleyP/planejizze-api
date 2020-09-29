package br.com.planejizze.service;

import br.com.planejizze.dto.Receita30DayDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Receita;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ReceitaService extends AbstractService<Receita, Long, ReceitaRepository> {
    @Autowired
    public ReceitaService(ReceitaRepository receitaRepository) {
        super(receitaRepository);
    }

    @Override
    public Receita save(Receita entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoRecebimento().setReceita(entity);
        return repo.save(entity);
    }

    @Override
    public Receita update(Receita entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoRecebimento().setReceita(entity);
        return repo.save(entity);
    }

    @Override
    public List<Receita> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<Receita> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public Receita findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }

    public Receita30DayDTO findReceitasLast30Days(Long userId) {
        return new Receita30DayDTO(repo.findReceitasLast30Days(userId));
    }

    public Receita30DayDTO findReceitasNext30Days(Long userId) {
        return new Receita30DayDTO(repo.findReceitasNext30Days(userId));
    }

    public Receita30DayDTO findNextReceita(Long userId) {
        return new Receita30DayDTO(repo.findNextReceita(userId));
    }
}

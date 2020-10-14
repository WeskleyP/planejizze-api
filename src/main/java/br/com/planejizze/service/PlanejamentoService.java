package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.exceptions.PlanejamentoInvalidDate;
import br.com.planejizze.model.Planejamento;
import br.com.planejizze.model.PlanejamentoCategoria;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.model.pk.PlanejamentoCategoriaPK;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanejamentoService extends AbstractService<Planejamento, Long, PlanejamentoRepository> {
    @Autowired
    public PlanejamentoService(PlanejamentoRepository planejamentoRepository) {
        super(planejamentoRepository);
    }

    @Override
    public Planejamento save(Planejamento entity, HttpServletRequest request) {
        if (repo.findPlanejamentoIdFromDate(TokenUtils.from(request).getUserId(),
                entity.getDataInicio(), entity.getDataFim()) == null){
            throw new PlanejamentoInvalidDate("Você já possui um planejamento no periodo selecionado");
        }
        return getPlanejamento(entity, request);
    }

    @Override
    public Planejamento update(Planejamento entity, HttpServletRequest request) {
        var planId = repo.findPlanejamentoIdFromDate(TokenUtils.from(request).getUserId(),
                entity.getDataInicio(), entity.getDataFim());
        if (planId == null || !planId.equals(entity.getId())){
            throw new PlanejamentoInvalidDate("Você já possui um planejamento no periodo selecionado");
        }
        return getPlanejamento(entity, request);
    }

    private Planejamento getPlanejamento(Planejamento entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        Planejamento plan = repo.save(new Planejamento(entity.getId(), entity.getDescricao(), entity.getAlertaPorcentagem(),
                entity.getReceitaTotal(), entity.getMetaGastos(), entity.getDataInicio(),
                entity.getDataFim(), entity.getUsuario()));
        List<PlanejamentoCategoria> planejamentoCategoria = new ArrayList<>();
        for (PlanejamentoCategoria categoria : entity.getCategorias()) {
            PlanejamentoCategoria pl = new PlanejamentoCategoria(new PlanejamentoCategoriaPK(
                    plan, categoria.getPlanejamentoCategoriaPK().getCategoriaDespesa()),
                    categoria.getValorMaximoGasto());
            planejamentoCategoria.add(pl);
        }
        plan.setCategorias(planejamentoCategoria);
        return repo.save(plan);
    }

    @Override
    public List<Planejamento> findAll(HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId());
    }

    @Override
    public Page<Planejamento> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return repo.findAllByUsuarioIdOrUsuarioIdIsNull(TokenUtils.from(request).getUserId(), pageable);
    }

    @Override
    public Planejamento findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return repo.findByIdAndUsuarioIdOrUsuarioIsNull(aLong, TokenUtils.from(request).getUserId())
                .orElseThrow(() -> new NotFoundException("Dados não encontrados! Id: " + aLong));
    }
}

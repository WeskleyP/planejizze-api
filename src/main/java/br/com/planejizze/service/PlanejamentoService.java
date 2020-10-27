package br.com.planejizze.service;

import br.com.planejizze.dto.PlanejamentoDropdownDTO;
import br.com.planejizze.dto.PlanejamentoPrevistoRealDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.exceptions.PlanejamentoInvalidDate;
import br.com.planejizze.model.Planejamento;
import br.com.planejizze.model.PlanejamentoCategoria;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.model.pk.PlanejamentoCategoriaPK;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;

@Service
public class PlanejamentoService extends AbstractService<Planejamento, Long, PlanejamentoRepository> {
    @Autowired
    public PlanejamentoService(PlanejamentoRepository planejamentoRepository) {
        super(planejamentoRepository);
    }

    @Override
    public Planejamento save(Planejamento entity, HttpServletRequest request) {
        if (repo.findPlanejamentoIdFromDate(TokenUtils.from(request).getUserId(),
                entity.getDataInicio(), entity.getDataFim()) != null) {
            throw new PlanejamentoInvalidDate("Você já possui um planejamento no periodo selecionado");
        }
        return getPlanejamentoValidate(entity, request);
    }

    @Override
    public Planejamento update(Planejamento entity, HttpServletRequest request) {
        var planId = repo.findPlanejamentoIdFromDate(TokenUtils.from(request).getUserId(),
                entity.getDataInicio(), entity.getDataFim());
        if (planId == null || !planId.equals(entity.getId())) {
            throw new PlanejamentoInvalidDate("Você já possui um planejamento no periodo selecionado");
        }
        return getPlanejamentoValidate(entity, request);
    }

    private Planejamento getPlanejamentoValidate(Planejamento entity, HttpServletRequest request) {
        var calInicio = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calInicio.setTime(entity.getDataInicio());
        var calFim = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calFim.setTime(entity.getDataFim());
        var max = calInicio.getActualMaximum(Calendar.DATE);
        var min = calInicio.getActualMinimum(Calendar.DATE);
        if (calInicio.get(Calendar.MONTH) != calFim.get(Calendar.MONTH)) {
            throw new PlanejamentoInvalidDate("Os meses das data de inicio e fim são diferentes");
        }
        if (min != calInicio.get(Calendar.DAY_OF_MONTH) || max != calFim.get(Calendar.DAY_OF_MONTH)) {
            throw new PlanejamentoInvalidDate("Os dias do mes não são equivalentes ao primeiro ou ultimo dia do mês");
        }
        return getPlanejamento(entity, request);
    }

    private Planejamento getPlanejamento(Planejamento entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        Planejamento plan = repo.save(new Planejamento(entity.getId(), entity.getDescricao(), entity.getAlertaPorcentagem(),
                entity.getMetaGastos(), entity.getDataInicio(), entity.getDataFim(), entity.getUsuario()));
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

    public List<PlanejamentoDropdownDTO> findAllDropdown(Long userId) {
        return repo.findAllDropdown(userId);
    }

    public PlanejamentoPrevistoRealDTO findPlanejamentoPrevistoDespesaReal(Long planejamentoId, Long userId) throws JsonProcessingException {
        var string = repo.findPlanejamentoPrevistoDespesaReal(planejamentoId, userId);
        return new ObjectMapper().readValue(string, PlanejamentoPrevistoRealDTO.class);
    }

    public PlanejamentoPrevistoRealDTO findLastPlanejamento(Long userId) throws JsonProcessingException {
        var string = repo.findLastPlanejamento(userId);
        return new ObjectMapper().readValue(string, PlanejamentoPrevistoRealDTO.class);
    }
}

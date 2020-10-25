package br.com.planejizze.service;

import br.com.planejizze.dto.Receita30DayDTO;
import br.com.planejizze.dto.Receita6MonthsDTO;
import br.com.planejizze.dto.ReceitaPorCategoriaDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.*;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceitaService extends AbstractService<Receita, Long, ReceitaRepository> {
    @Autowired
    public ReceitaService(ReceitaRepository receitaRepository) {
        super(receitaRepository);
    }

    @Override
    public Receita save(Receita entity, HttpServletRequest request) {
        return getReceita(entity, request);
    }

    @Override
    public Receita update(Receita entity, HttpServletRequest request) {
        return getReceita(entity, request);
    }

    private Receita getReceita(Receita entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoRecebimento().setReceita(entity);
        if (entity.getTipoRecebimento() instanceof TipoRecebimentoBanco) {
            ((TipoRecebimentoBanco) entity.getTipoRecebimento()).getTipoRecebimentoBancoLogs()
                    .forEach(tipoRecebimentoBancoLog -> tipoRecebimentoBancoLog.setTipoRecebimentoBanco((TipoRecebimentoBanco) entity.getTipoRecebimento()));
        } else if (entity.getTipoRecebimento() instanceof TipoRecebimentoMoeda) {
            ((TipoRecebimentoMoeda) entity.getTipoRecebimento()).getTipoRecebimentoMoedaLogs()
                    .forEach(tipoRecebimentoMoedaLog -> tipoRecebimentoMoedaLog.setTipoRecebimentoMoeda((TipoRecebimentoMoeda) entity.getTipoRecebimento()));
        }
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

    public List<Receita6MonthsDTO> findReceitasLast6Months(Long userId) throws JsonProcessingException {
        List<Receita6MonthsDTO> receita6MonthsDTO = new ArrayList<>();
        for (String list : repo.findReceitasLast6Months(userId)) {
            receita6MonthsDTO.add(new ObjectMapper().readValue(list, Receita6MonthsDTO.class));
        }
        return receita6MonthsDTO;
    }

    public List<ReceitaPorCategoriaDTO> porCategoriaEMês(Long userId, Long mes) throws JsonProcessingException {
        List<ReceitaPorCategoriaDTO> receita6MonthsDTO = new ArrayList<>();
        for (String list : repo.findReceitasPorCategoriaEMes(userId, mes)) {
            receita6MonthsDTO.add(new ObjectMapper().readValue(list, ReceitaPorCategoriaDTO.class));
        }
        return receita6MonthsDTO;
    }
}

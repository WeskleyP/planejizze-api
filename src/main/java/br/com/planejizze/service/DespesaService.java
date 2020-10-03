package br.com.planejizze.service;

import br.com.planejizze.dto.Despesa30DayDTO;
import br.com.planejizze.dto.Despesa6MonthsDTO;
import br.com.planejizze.dto.DespesaPorCategoriaDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Despesa;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.DespesaRepository;
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

    public Despesa30DayDTO findDespesasLast30Days(Long userId) {
        return new Despesa30DayDTO(repo.findDespesasLast30Days(userId));
    }

    public Despesa30DayDTO findDespesasNext30Days(Long userId) {
        return new Despesa30DayDTO(repo.findDespesasNext30Days(userId));
    }

    public Despesa30DayDTO findNextDespesas(Long userId) {
        return new Despesa30DayDTO(repo.findNextDespesa(userId));
    }

    public List<Despesa6MonthsDTO> findDespesasLast6Months(Long userId) throws JsonProcessingException {
        List<Despesa6MonthsDTO> receita6MonthsDTO = new ArrayList<>();
        for (String list : repo.findDespesasLast6Months(userId)) {
            receita6MonthsDTO.add(new ObjectMapper().readValue(list, Despesa6MonthsDTO.class));
        }
        return receita6MonthsDTO;
    }

    public List<DespesaPorCategoriaDTO> porCategoriaEMês(Long userId, Long mes) throws JsonProcessingException {
        List<DespesaPorCategoriaDTO> receita6MonthsDTO = new ArrayList<>();
        for (String list : repo.findDespesasPorCategoriaEMes(userId, mes)) {
            receita6MonthsDTO.add(new ObjectMapper().readValue(list, DespesaPorCategoriaDTO.class));
        }
        return receita6MonthsDTO;
    }
}

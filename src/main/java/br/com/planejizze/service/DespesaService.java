package br.com.planejizze.service;

import br.com.planejizze.dto.Despesa30DayDTO;
import br.com.planejizze.dto.Despesa6MonthsDTO;
import br.com.planejizze.dto.DespesaPorCategoriaDTO;
import br.com.planejizze.exceptions.BadParamsException;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Despesa;
import br.com.planejizze.model.TipoPagamentoCartao;
import br.com.planejizze.model.TipoPagamentoMoeda;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DespesaService extends AbstractService<Despesa, Long, DespesaRepository> {
    @Autowired
    public DespesaService(DespesaRepository despesaRepository) {
        super(despesaRepository);
    }

    @Override
    public Despesa save(Despesa entity, HttpServletRequest request) {
        return getDespesa(entity, request);
    }

    @Override
    public Despesa update(Despesa entity, HttpServletRequest request) {
        return getDespesa(entity, request);
    }

    private Despesa getDespesa(Despesa entity, HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setId(TokenUtils.from(request).getUserId());
        entity.setUsuario(usuario);
        entity.getTipoPagamento().setDespesa(entity);
        if (entity.getTipoPagamento() instanceof TipoPagamentoCartao) {
            ((TipoPagamentoCartao) entity.getTipoPagamento()).getTipoPagamentoCartaoParcelas()
                    .forEach(tipoPagamentoCartaoParcelas ->
                            tipoPagamentoCartaoParcelas.setTipoPagamentoCartao((TipoPagamentoCartao) entity.getTipoPagamento()));
        } else if (entity.getTipoPagamento() instanceof TipoPagamentoMoeda) {
            ((TipoPagamentoMoeda) entity.getTipoPagamento()).getTipoPagamentoMoedaLogs()
                    .forEach(tipoPagamentoMoedaLog ->
                            tipoPagamentoMoedaLog.setTipoPagamentoMoeda((TipoPagamentoMoeda) entity.getTipoPagamento()));
        }
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
        List<Despesa6MonthsDTO> despesa6MonthsDTO = new ArrayList<>();
        for (String list : repo.findDespesasLast6Months(userId)) {
            despesa6MonthsDTO.add(new ObjectMapper().readValue(list, Despesa6MonthsDTO.class));
        }
        return despesa6MonthsDTO;
    }

    public List<DespesaPorCategoriaDTO> porCategoriaEMêsECartao(Long userId, Long mes, Long cartao) throws JsonProcessingException {
        List<DespesaPorCategoriaDTO> despesaCartaoMes = new ArrayList<>();
        for (String list : repo.findDespesasPorCategoriaEMesECartao(userId, mes, cartao)) {
            despesaCartaoMes.add(new ObjectMapper().readValue(list, DespesaPorCategoriaDTO.class));
        }
        return despesaCartaoMes;
    }

    public Integer updateDespesaStatusCartao(Long id) {
        return repo.updateDespesaStatusCartao(id);
    }

    public Integer updateDespesaStatusMoeda(Long id) {
        return repo.updateDespesaStatusMoeda(id);
    }

    public List<Despesa> findDespesasForDashboard(Long userId, Long days) {
        if (days != 7 && days != 15 && days != 30 && days != 90 && days != 180 && days != 360) {
            throw new BadParamsException("Os dias informados não são os dias padrões necessários!");
        }
        Date date = DateUtils.addDays(new Date(), Math.toIntExact(Math.subtractExact(days, (days * 2))));
        return repo.findDespesasForDashboard(userId, date);
    }

    public List<DespesaPorCategoriaDTO> porCategoriaEMês(Long userId, Long mes) throws JsonProcessingException {
        List<DespesaPorCategoriaDTO> despesaCartaoMes = new ArrayList<>();
        for (String list : repo.findDespesasPorCategoriaEMes(userId, mes)) {
            despesaCartaoMes.add(new ObjectMapper().readValue(list, DespesaPorCategoriaDTO.class));
        }
        return despesaCartaoMes;
    }
}

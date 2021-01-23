package br.com.planejizze.service;

import br.com.planejizze.enums.StatusDespesa;
import br.com.planejizze.enums.StatusReceita;
import br.com.planejizze.model.*;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.repository.ReceitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public ScheduleService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    @Scheduled(cron = "0 0 0/6 1/1 * ?")
    public void validaçãoDeStatusDeReceita() {
        logger.info("Iniciando validação de status de todas as receitas");
        var logsBanco = new ArrayList<TipoRecebimentoBancoLog>();
        var logsMoeda = new ArrayList<TipoRecebimentoMoedaLog>();
        var receitas = receitaRepository.findAllWhereStatusIsAreceber();
        receitas.forEach(receita -> {
            if (receita.getTipoRecebimento() instanceof TipoRecebimentoBanco) {
                logsBanco.addAll(((TipoRecebimentoBanco) receita.getTipoRecebimento()).getTipoRecebimentoBancoLogs());
            }
            if (receita.getTipoRecebimento() instanceof TipoRecebimentoMoeda) {
                logsMoeda.addAll(((TipoRecebimentoMoeda) receita.getTipoRecebimento()).getTipoRecebimentoMoedaLogs());
            }
        });
        if (logsBanco.size() > 0) {
            for (var b : logsBanco) {
                if (b.getStatusReceita().equals(StatusReceita.A_RECEBER)) {
                    if (b.getDataRecebimentoExperada().after(Date.from(Instant.now().plus(12, ChronoUnit.HOURS)))) {
                        b.setStatusReceita(StatusReceita.ATRASADA);
                    }
                }
            }
        }
        if (logsMoeda.size() > 0) {
            for (var m : logsMoeda) {
                if (m.getStatusReceita().equals(StatusReceita.A_RECEBER)) {
                    if (m.getDataRecebimentoExperada().after(Date.from(Instant.now().plus(12, ChronoUnit.HOURS)))) {
                        m.setStatusReceita(StatusReceita.ATRASADA);
                    }
                }
            }
        }
        for (var r : receitas) {
            if (r.getTipoRecebimento() instanceof TipoRecebimentoBanco) {
                ((TipoRecebimentoBanco) r.getTipoRecebimento()).setTipoRecebimentoBancoLogs(logsBanco);
            }
            if (r.getTipoRecebimento() instanceof TipoRecebimentoMoeda) {
                ((TipoRecebimentoMoeda) r.getTipoRecebimento()).setTipoRecebimentoMoedaLogs(logsMoeda);
            }
        }
        receitaRepository.saveAll(receitas);
        logger.info("Finalizando validação de status de todas as receitas");
    }

    @Scheduled(cron = "0 0 0/6 1/1 * ?")
    public void validaçãoDeStatusDeDespesa() {
        logger.info("Iniciando validação de status de todas as despesas");
        var logsCartao = new ArrayList<TipoPagamentoCartaoParcelas>();
        var logsMoeda = new ArrayList<TipoPagamentoMoedaLog>();
        var despesas = despesaRepository.findAllWhereStatusIsApagar();
        despesas.forEach(despesa -> {
            if (despesa.getTipoPagamento() instanceof TipoPagamentoCartao) {
                logsCartao.addAll(((TipoPagamentoCartao) despesa.getTipoPagamento()).getTipoPagamentoCartaoParcelas());
            }
            if (despesa.getTipoPagamento() instanceof TipoPagamentoMoeda) {
                logsMoeda.addAll(((TipoPagamentoMoeda) despesa.getTipoPagamento()).getTipoPagamentoMoedaLogs());
            }
        });
        if (logsCartao.size() > 0) {
            for (var c : logsCartao) {
                if (c.getStatusDespesa().equals(StatusDespesa.A_PAGAR)) {
                    if (c.getDataPagamentoExperada().after(Date.from(Instant.now().plus(12, ChronoUnit.HOURS)))) {
                        c.setStatusDespesa(StatusDespesa.ATRASADA);
                    }
                }
            }
        }
        if (logsMoeda.size() > 0) {
            for (var m : logsMoeda) {
                if (m.getStatusDespesa().equals(StatusDespesa.A_PAGAR)) {
                    if (m.getDataPagamentoExperada().after(Date.from(Instant.now().plus(12, ChronoUnit.HOURS)))) {
                        m.setStatusDespesa(StatusDespesa.ATRASADA);
                    }
                }
            }
        }
        for (var d : despesas) {
            if (d.getTipoPagamento() instanceof TipoPagamentoCartao) {
                ((TipoPagamentoCartao) d.getTipoPagamento()).setTipoPagamentoCartaoParcelas(logsCartao);
            }
            if (d.getTipoPagamento() instanceof TipoPagamentoMoeda) {
                ((TipoPagamentoMoeda) d.getTipoPagamento()).setTipoPagamentoMoedaLogs(logsMoeda);
            }
        }
        despesaRepository.saveAll(despesas);
        logger.info("Finalizando validação de status de todas as despesas");
    }
}

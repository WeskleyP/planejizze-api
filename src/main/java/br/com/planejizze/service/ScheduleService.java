package br.com.planejizze.service;

import br.com.planejizze.enums.StatusDespesa;
import br.com.planejizze.enums.StatusReceita;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.repository.ReceitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

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
        var receitas = receitaRepository.findAllWhereStatusIsAreceber().stream()
                .filter(receita -> receita.getStatusReceita().equals(StatusReceita.A_RECEBER))
                .collect(Collectors.toList());
        for (var r : receitas) {
            if (r.getDataRecebimento().toInstant().isAfter(Instant.now().plus(12, ChronoUnit.HOURS))) {
                r.setStatusReceita(StatusReceita.ATRASADA);
            }
        }
        receitaRepository.saveAll(receitas);
        logger.info("Finalizando validação de status de todas as receitas");
    }

    @Scheduled(cron = "0 0 0/6 1/1 * ?")
    public void validaçãoDeStatusDeDespesa() {
        logger.info("Iniciando validação de status de todas as despesas");
        var despesas = despesaRepository.findAllWhereStatusIsApagar().stream()
                .filter(receita -> receita.getStatusDespesa().equals(StatusDespesa.A_PAGAR))
                .collect(Collectors.toList());
        for (var r : despesas) {
            if (r.getDataVencimento().toInstant().isAfter(Instant.now().plus(12, ChronoUnit.HOURS))) {
                r.setStatusDespesa(StatusDespesa.PAGO);
            }
        }
        despesaRepository.saveAll(despesas);
        logger.info("Finalizando validação de status de todas as despesas");
    }
}

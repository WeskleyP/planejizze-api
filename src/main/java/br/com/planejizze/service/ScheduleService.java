package br.com.planejizze.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    // TODO fazer ambos os metodos de scheduling
    @Scheduled(cron = "0 0 0/6 1/1 * ?")
    public void validaçãoDeStatusDeReceita() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }

    @Scheduled(cron = "0 0 0/6 1/1 * ?")
    public void validaçãoDeStatusDeDespesa() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }
}

package br.com.planejizze.resource;

import br.com.planejizze.dto.Receita30DayDTO;
import br.com.planejizze.dto.Receita6MonthsDTO;
import br.com.planejizze.dto.ReceitaPorCategoriaDTO;
import br.com.planejizze.exceptions.BadParamsException;
import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.service.ReceitaService;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Receita")
@RestController
@RequestMapping(path = "/receita")
public class ReceitaResource extends AbstractResource<Receita, Long, ReceitaRepository, ReceitaService> {
    private final ReceitaService receitaService;

    @Autowired
    protected ReceitaResource(ReceitaService service) {
        super(service);
        this.receitaService = service;
    }

    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasLast30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasLast30Days(TokenUtils.from(request).getUserId()));
    }

    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasNext30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasNext30Days(TokenUtils.from(request).getUserId()));
    }

    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next")
    public ResponseEntity<Receita30DayDTO> findNextReceita(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findNextReceita(TokenUtils.from(request).getUserId()));
    }

    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last6Months")
    public ResponseEntity<List<Receita6MonthsDTO>> findReceitasLast6Months(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(receitaService.findReceitasLast6Months(TokenUtils.from(request).getUserId()));
    }

    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/byCategoria")
    public ResponseEntity<List<ReceitaPorCategoriaDTO>> porCategoriaEMês(HttpServletRequest request,
                                                                         @RequestParam("mes") Long mes) throws JsonProcessingException {
        if (!(mes >= 1 & mes <= 12)) throw new BadParamsException("O mês deve estar entre 1 e 12");
        return ResponseEntity.ok(receitaService.porCategoriaEMês(TokenUtils.from(request).getUserId(), mes));
    }
}

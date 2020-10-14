package br.com.planejizze.resource;

import br.com.planejizze.dto.Despesa30DayDTO;
import br.com.planejizze.dto.Despesa6MonthsDTO;
import br.com.planejizze.dto.DespesaPorCategoriaDTO;
import br.com.planejizze.exceptions.BadParamsException;
import br.com.planejizze.model.Despesa;
import br.com.planejizze.repository.DespesaRepository;
import br.com.planejizze.service.DespesaService;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Despesa")
@RestController
@RequestMapping(path = "/despesa")
public class DespesaResource extends AbstractResource<Despesa, Long, DespesaRepository, DespesaService> {
    private final DespesaService despesaService;

    @Autowired
    protected DespesaResource(DespesaService service) {
        super(service);
        this.despesaService = service;
    }

    @ApiOperation("Busca as despesas do últimos 30 dias")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last30Days")
    public ResponseEntity<Despesa30DayDTO> findDespesasLast30Days(HttpServletRequest request) {
        return ResponseEntity.ok(despesaService.findDespesasLast30Days(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as despesas do próximos 30 dias")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next30Days")
    public ResponseEntity<Despesa30DayDTO> findDespesasNext30Days(HttpServletRequest request) {
        return ResponseEntity.ok(despesaService.findDespesasNext30Days(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca a próxima despesa a pagar")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next")
    public ResponseEntity<Despesa30DayDTO> findNextDespesas(HttpServletRequest request) {
        return ResponseEntity.ok(despesaService.findNextDespesas(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as despesas do ultimos 6 meses")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last6Months")
    public ResponseEntity<List<Despesa6MonthsDTO>> findDespesasLast6Months(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(despesaService.findDespesasLast6Months(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as despesas agrupadas por categoria e por determinado mes")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/byCategoria")
    public ResponseEntity<List<DespesaPorCategoriaDTO>> porCategoriaEMês(HttpServletRequest request,
                                                                         @RequestParam("mes") Long mes) throws JsonProcessingException {
        if (!(mes >= 1 & mes <= 12)) throw new BadParamsException("O mês deve estar entre 1 e 12");
        return ResponseEntity.ok(despesaService.porCategoriaEMês(TokenUtils.from(request).getUserId(), mes));
    }
}

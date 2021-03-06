package br.com.planejizze.resource;

import br.com.planejizze.dto.Receita30DayDTO;
import br.com.planejizze.dto.Receita6MonthsDTO;
import br.com.planejizze.dto.ReceitaPorCategoriaDTO;
import br.com.planejizze.exceptions.BadParamsException;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.service.ReceitaService;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("Busca as receitas do últimos 30 dias")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasLast30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasLast30Days(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as receitas do próximos 30 dias")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasNext30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasNext30Days(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca a próxima despesa a receber")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/next")
    public ResponseEntity<Receita30DayDTO> findNextReceita(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findNextReceita(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as receitas do ultimos 6 meses")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/last6Months")
    public ResponseEntity<List<Receita6MonthsDTO>> findReceitasLast6Months(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(receitaService.findReceitasLast6Months(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Busca as receitas agrupadas por categoria e por determinado mes")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/byCategoria")
    public ResponseEntity<List<ReceitaPorCategoriaDTO>> porCategoriaEMes(HttpServletRequest request,
                                                                         @RequestParam("mes") Long mes) throws JsonProcessingException {
        if (!(mes >= 1 & mes <= 12)) throw new BadParamsException("O mês deve estar entre 1 e 12");
        return ResponseEntity.ok(receitaService.porCategoriaEMes(TokenUtils.from(request).getUserId(), mes));
    }

    @ApiOperation("Altera o status de receitas com tipo de recebimento = banco")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'update')")
    @PutMapping(path = "/updateReceitaBanco/{id}")
    public ResponseEntity updateReceitaStatusBanco(@PathVariable Long id) {
        if (receitaService.updateReceitaStatusBanco(id) > 0) {
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Não foi possivel alterar o status da receita!");
    }

    @ApiOperation("Altera o status de receitas com tipo de recebimento = moeda")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'update')")
    @PutMapping(path = "/updateReceitaMoeda/{id}")
    public ResponseEntity updateReceitaStatusMoeda(@PathVariable Long id) {
        if (receitaService.updateReceitaStatusMoeda(id) > 0) {
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Não foi possivel alterar o status da receita!");
    }

    @ApiOperation("Busca as receitas para exibir no gráfico do dashboard")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/dashboard")
    public ResponseEntity<List<Receita>> dashboard(HttpServletRequest request, @RequestParam("days") Long days) {
        return ResponseEntity.ok(receitaService.findReceitasForDashboard(TokenUtils.from(request).getUserId(), days));
    }
}

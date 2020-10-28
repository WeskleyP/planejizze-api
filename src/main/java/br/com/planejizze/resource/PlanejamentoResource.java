package br.com.planejizze.resource;

import br.com.planejizze.dto.PlanejamentoDropdownDTO;
import br.com.planejizze.dto.PlanejamentoPrevistoRealDTO;
import br.com.planejizze.model.Planejamento;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.service.PlanejamentoService;
import br.com.planejizze.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Planejamento")
@RestController
@RequestMapping(path = "/planejamento")
public class PlanejamentoResource extends AbstractResource<Planejamento, Long, PlanejamentoRepository, PlanejamentoService> {
    private final PlanejamentoService planejamentoService;

    @Autowired
    protected PlanejamentoResource(PlanejamentoService service) {
        super(service);
        this.planejamentoService = service;
    }

    @ApiOperation("Lista todos os planejamentos para uma lista dropdown para " +
            "escolher qual vai ser buscada no metodo 'findPlanejamentoPrevistoDespesaReal'")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/findAllDropdown")
    public ResponseEntity<List<PlanejamentoDropdownDTO>> findAllDropdown(HttpServletRequest request) {
        return ResponseEntity.ok(planejamentoService.findAllDropdown(TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Lista um planejamento e seus detalhes, juntamente com valores gastos nas despesas dele")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/previstoReal/{id}")
    public ResponseEntity<PlanejamentoPrevistoRealDTO> findPlanejamentoPrevistoDespesaReal(@PathVariable Long id,
                                                                                           HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(planejamentoService
                .findPlanejamentoPrevistoDespesaReal(id, TokenUtils.from(request).getUserId()));
    }

    @ApiOperation("Lista o ultimo planejamento (com base na data final) " +
            "e seus detalhes, juntamente com valores gastos nas despesas dele")
    @PreAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/lastPlanejamento")
    public ResponseEntity<PlanejamentoPrevistoRealDTO> findLastPlanejamento(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(planejamentoService
                .findLastPlanejamento(TokenUtils.from(request).getUserId()));
    }
}

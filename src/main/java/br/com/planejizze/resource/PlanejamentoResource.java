package br.com.planejizze.resource;

import br.com.planejizze.dto.PlanejamentoDropdownDTO;
import br.com.planejizze.model.Planejamento;
import br.com.planejizze.repository.PlanejamentoRepository;
import br.com.planejizze.service.PlanejamentoService;
import br.com.planejizze.utils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "/findAllDropdown")
    public ResponseEntity<List<PlanejamentoDropdownDTO>> findAllDropdown(HttpServletRequest request) {
        return ResponseEntity.ok(planejamentoService.findAllDropdown(TokenUtils.from(request).getUserId()));
    }

      @GetMapping(path = "/previsto_real")
    public ResponseEntity<List<PlanejamentoDropdownDTO>> findPlanejamentoPrevistoDespesaReal(HttpServletRequest request) {
        return ResponseEntity.ok(planejamentoService.findAllDropdown(TokenUtils.from(request).getUserId()));
    }
}

package br.com.planejizze.resource;

import br.com.planejizze.dto.Receita30DayDTO;
import br.com.planejizze.model.Receita;
import br.com.planejizze.repository.ReceitaRepository;
import br.com.planejizze.service.ReceitaService;
import br.com.planejizze.utils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping(path = "/last30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasLast30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasLast30Days(TokenUtils.from(request).getUserId()));
    }

    @GetMapping(path = "/next30Days")
    public ResponseEntity<Receita30DayDTO> findReceitasNext30Days(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findReceitasNext30Days(TokenUtils.from(request).getUserId()));
    }

    @GetMapping(path = "/next")
    public ResponseEntity<Receita30DayDTO> findNextReceita(HttpServletRequest request) {
        return ResponseEntity.ok(receitaService.findNextReceita(TokenUtils.from(request).getUserId()));
    }
}

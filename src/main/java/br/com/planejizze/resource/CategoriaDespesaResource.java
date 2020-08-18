package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaDespesaConverter;
import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import br.com.planejizze.service.CategoriaDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/categoriaDespesa")
public class CategoriaDespesaResource extends AbstractResource<CategoriaDespesa, Long, CategoriaDespesaRepository, CategoriaDespesaService> {

    private final CategoriaDespesaConverter converter;
    private final CategoriaDespesaService categoriaDespesaService;

    @Autowired
    protected CategoriaDespesaResource(CategoriaDespesaConverter converter, CategoriaDespesaService service) {
        super(service);
        this.converter = converter;
        this.categoriaDespesaService = service;
    }

    @Override
    public ResponseEntity<List> findAll() {
        List<CategoriaDespesa> categoriaDespesas = categoriaDespesaService.findAll();
        return ResponseEntity.ok(converter.convertListToDTO(categoriaDespesas));
    }
}

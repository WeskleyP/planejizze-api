package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaDespesaConverter;
import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import br.com.planejizze.service.CategoriaDespesaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Categorias de despesas")
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

    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @Override
    public ResponseEntity<List> findAll(HttpServletRequest request) {
        List<CategoriaDespesa> categoriaDespesas = categoriaDespesaService.findAll(request);
        return ResponseEntity.ok(converter.convertListToDTO(categoriaDespesas));
    }
}

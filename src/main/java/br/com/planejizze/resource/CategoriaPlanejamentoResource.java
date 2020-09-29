package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaPlanejamentoConverter;
import br.com.planejizze.model.CategoriaPlanejamento;
import br.com.planejizze.repository.CategoriaPlanejamentoRepository;
import br.com.planejizze.service.CategoriaPlanejamentoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Categorias de planejamento")
@RestController
@RequestMapping(path = "/categoriaPlanejamento")
public class CategoriaPlanejamentoResource extends AbstractResource<CategoriaPlanejamento, Long, CategoriaPlanejamentoRepository, CategoriaPlanejamentoService> {

    private final CategoriaPlanejamentoService categoriaPlanejamentoService;
    private final CategoriaPlanejamentoConverter categoriaPlanejamentoConverter;

    @Autowired
    protected CategoriaPlanejamentoResource(CategoriaPlanejamentoService service, CategoriaPlanejamentoConverter categoriaPlanejamentoConverter) {
        super(service);
        this.categoriaPlanejamentoService = service;
        this.categoriaPlanejamentoConverter = categoriaPlanejamentoConverter;
    }

    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @Override
    public ResponseEntity<List> findAll(HttpServletRequest request) {
        List<CategoriaPlanejamento> categoriaPlanejamentos = categoriaPlanejamentoService.findAll(request);
        return ResponseEntity.ok(categoriaPlanejamentoConverter.convertListToDTO(categoriaPlanejamentos));
    }
}

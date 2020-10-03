package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaReceitaConverter;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import br.com.planejizze.service.CategoriaReceitaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Categorias de receitas")
@RestController
@RequestMapping(path = "/categoriaReceita")
public class CategoriaReceitaResource extends AbstractResource<CategoriaReceita, Long, CategoriaReceitaRepository, CategoriaReceitaService> {
    private final CategoriaReceitaService categoriaReceitaService;
    private final CategoriaReceitaConverter categoriaReceitaConverter;

    @Autowired
    protected CategoriaReceitaResource(CategoriaReceitaService service, CategoriaReceitaConverter categoriaReceitaConverter) {
        super(service);
        this.categoriaReceitaService = service;
        this.categoriaReceitaConverter = categoriaReceitaConverter;
    }

    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @Override
    public ResponseEntity findAll(HttpServletRequest request) {
        return ResponseEntity.ok(categoriaReceitaService.findAll(request));
    }

    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @Override
    public ResponseEntity findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(categoriaReceitaService.findById(aLong, request));
    }
}

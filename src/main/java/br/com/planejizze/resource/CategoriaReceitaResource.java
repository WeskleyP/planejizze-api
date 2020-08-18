package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaReceitaConverter;
import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import br.com.planejizze.service.CategoriaReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Override
    public ResponseEntity<List> findAll() {
        List<CategoriaReceita> categoriaReceitas = categoriaReceitaService.findAll();
        return ResponseEntity.ok(categoriaReceitaConverter.convertListToDTO(categoriaReceitas));
    }
}

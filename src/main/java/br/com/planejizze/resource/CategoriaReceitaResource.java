package br.com.planejizze.resource;

import br.com.planejizze.converters.CategoriaReceitaConverter;
import br.com.planejizze.dto.CategoriaReceitaDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import br.com.planejizze.service.CategoriaReceitaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<CategoriaReceita> categoriaReceitas = categoriaReceitaService.findAll(request);
        List<CategoriaReceitaDTO> categoriaReceitaDTOS = categoriaReceitaConverter.convertListToDTO(categoriaReceitas);
        CollectionModel<CategoriaReceitaDTO> categoriaReceitaDTOCollectionModel =
                new CollectionModel<>(categoriaReceitaDTOS);
        categoriaReceitaDTOS.forEach(categoriaReceitaDTO -> categoriaReceitaDTO.add(linkTo(methodOn(CategoriaReceitaResource.class)
                .findById(categoriaReceitaDTO.getId(), request)).withSelfRel()));
        categoriaReceitaDTOCollectionModel
                .add(linkTo(methodOn(CategoriaReceitaResource.class).findAll(request)).withRel("categoriasReceitas"));
        return ResponseEntity.ok(categoriaReceitaDTOCollectionModel);
    }

    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @Override
    public ResponseEntity findById(Long aLong, HttpServletRequest request) throws NotFoundException {
        CategoriaReceita categoriaReceita = categoriaReceitaService.findById(aLong, request);
        CategoriaReceitaDTO categoriaReceitaDTO = categoriaReceitaConverter.convertToDTO(categoriaReceita);
        categoriaReceitaDTO
                .add(linkTo(methodOn(CategoriaReceitaResource.class).update(categoriaReceita, request)).withSelfRel());
        categoriaReceitaDTO
                .add(linkTo(methodOn(CategoriaReceitaResource.class).findAll(request)).withRel("categoriasReceitas"));
        return ResponseEntity.ok(categoriaReceitaDTO);
    }
}

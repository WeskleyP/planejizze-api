package br.com.planejizze.resource;

import br.com.planejizze.model.CategoriaReceita;
import br.com.planejizze.repository.CategoriaReceitaRepository;
import br.com.planejizze.service.CategoriaReceitaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Categorias de receitas")
@RestController
@RequestMapping(path = "/categoriaReceita")
public class CategoriaReceitaResource extends AbstractResource<CategoriaReceita, Long, CategoriaReceitaRepository, CategoriaReceitaService> {

    @Autowired
    protected CategoriaReceitaResource(CategoriaReceitaService service) {
        super(service);
    }

}
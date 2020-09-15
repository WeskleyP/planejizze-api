package br.com.planejizze.resource;

import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.UsuarioRepository;
import br.com.planejizze.service.UsuarioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Usuário")
@RestController
@RequestMapping(path = "/usuario")
public class UsuarioResource extends AbstractResource<Usuario, Long, UsuarioRepository, UsuarioService> {
    private final UsuarioService usuarioService;

    @Autowired
    protected UsuarioResource(UsuarioService service) {
        super(service);
        this.usuarioService = service;
    }
}

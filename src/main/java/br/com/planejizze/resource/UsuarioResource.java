package br.com.planejizze.resource;

import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.UsuarioRepository;
import br.com.planejizze.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation("Busca a quantidade de usuário que possuem determinadas roles")
    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/usersCountByRole")
    public ResponseEntity findUsersCountByRole() throws JsonProcessingException {
        return ResponseEntity.ok(usuarioService.findUsersCountByRole());
    }
}

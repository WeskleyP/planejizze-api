package br.com.planejizze.resource;

import br.com.planejizze.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/role")
public class RoleResource {

    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("Busca todas as roles")
    @PostAuthorize(value = "hasPermission(#this.this.class.simpleName, 'read')")
    @GetMapping(path = "/findAll")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }
}

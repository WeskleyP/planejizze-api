package br.com.planejizze.resource;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.service.AbstractService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractResource<T, ID, REPO extends JpaRepository<T, ID>, SERVICE extends AbstractService<T, ID, REPO>> {

    protected final SERVICE service;

    protected AbstractResource(SERVICE service) {
        this.service = service;
    }

    @ApiOperation("Salvar o objeto")
    @PostMapping(path = "/save")
    public ResponseEntity save(@RequestBody T entity, HttpServletRequest request) {
        return ResponseEntity.ok(service.save(entity, request));
    }

    @ApiOperation("Atualizar o objeto")
    @PutMapping(path = "/update")
    public ResponseEntity update(@RequestBody T entity, HttpServletRequest request) {
        return ResponseEntity.ok(service.update(entity, request));
    }

    @ApiOperation("Buscar o objeto pelo id")
    @GetMapping(path = "/byId/{id}")
    public ResponseEntity findById(@PathVariable ID id, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id, request));
    }

    @ApiOperation("Excluir o objeto")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id, HttpServletRequest request) {
        service.deleteById(id, request);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Buscar todos os objetos")
    @GetMapping(path = "/findAll")
    public ResponseEntity<List> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(service.findAll(request));
    }

    @ApiOperation("Buscar todos os objetos de forma paginada")
    @GetMapping(path = "/paginated")
    public ResponseEntity<Page> findAllWithPagination(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity.ok(service.findAllWithPagination(pageable, request));
    }

    @ApiOperation("Verificar se o objeto existe com base no seu id")
    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable ID id, HttpServletRequest request) {
        return ResponseEntity.ok(service.existsById(id, request));
    }
}

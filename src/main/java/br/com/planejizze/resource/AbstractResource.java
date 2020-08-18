package br.com.planejizze.resource;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractResource<T, ID, REPO extends JpaRepository<T, ID>, SERVICE extends AbstractService<T, ID, REPO>> {

    protected final SERVICE service;

    protected AbstractResource(SERVICE service) {
        this.service = service;
    }

    @PostMapping(path = "/save")
    public ResponseEntity save(@RequestBody T entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PutMapping(path = "/update")
    public ResponseEntity update(@RequestBody T entity) {
        return ResponseEntity.ok(service.update(entity));
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity findById(@PathVariable ID id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/findAll")
    public ResponseEntity<List> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/paginated")
    public ResponseEntity<Page> findAllWithPagination(Pageable pageable) {
        return ResponseEntity.ok(service.findAllWithPagination(pageable));
    }

    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable ID id) {
        return ResponseEntity.ok(service.existsById(id));
    }
}

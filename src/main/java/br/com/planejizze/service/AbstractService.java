package br.com.planejizze.service;

import br.com.planejizze.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T, ID, REPO extends JpaRepository<T, ID>> {

    protected final REPO repo;

    public AbstractService(REPO repo) {
        this.repo = repo;
    }

    @Transactional(rollbackFor = Exception.class)
    public T save(T entity) {
        return repo.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public T update(T entity) {
        return repo.save(entity);
    }

    @Transactional(readOnly = true)
    public T findById(ID id) throws NotFoundException {
        return repo.findById(id).orElseThrow(() ->
                new NotFoundException("Dados não encontrados! Id: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(ID id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Page<T> findAllWithPagination(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Boolean existsById(ID id) {
        return repo.existsById(id);
    }

}

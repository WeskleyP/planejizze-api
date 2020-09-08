package br.com.planejizze.repository;

import br.com.planejizze.model.CategoriaReceita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Long> {

    List<CategoriaReceita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<CategoriaReceita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<CategoriaReceita> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

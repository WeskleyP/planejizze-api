package br.com.planejizze.repository;

import br.com.planejizze.model.CategoriaDespesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {

    List<CategoriaDespesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<CategoriaDespesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<CategoriaDespesa> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

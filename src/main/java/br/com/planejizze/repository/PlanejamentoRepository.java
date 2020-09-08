package br.com.planejizze.repository;

import br.com.planejizze.model.Planejamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
    List<Planejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Planejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Planejamento> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

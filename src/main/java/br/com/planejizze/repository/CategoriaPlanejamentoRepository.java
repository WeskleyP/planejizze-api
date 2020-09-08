package br.com.planejizze.repository;

import br.com.planejizze.model.CategoriaPlanejamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaPlanejamentoRepository extends JpaRepository<CategoriaPlanejamento, Long> {

    List<CategoriaPlanejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<CategoriaPlanejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<CategoriaPlanejamento> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

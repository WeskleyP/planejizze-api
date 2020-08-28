package br.com.planejizze.repository;

import br.com.planejizze.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Despesa findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

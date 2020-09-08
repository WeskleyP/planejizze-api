package br.com.planejizze.repository;

import br.com.planejizze.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Receita> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}

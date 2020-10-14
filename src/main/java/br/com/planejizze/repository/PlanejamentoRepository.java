package br.com.planejizze.repository;

import br.com.planejizze.model.Planejamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
    List<Planejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Planejamento> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Planejamento> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);

    @Query(value = "select p.id from Planejamento p where p.usuario.id = ?1 " +
            " and ((p.dataInicio <= ?2 and p.dataFim >= ?2) " +
            "or (p.dataFim >= ?3 and p.dataInicio <= ?3 ) " +
            "or (p.dataInicio >= ?2 and p.dataFim <= ?3 ))")
    Long findPlanejamentoIdFromDate(Long usuarioId, Date dataInicio, Date dataFim);
}

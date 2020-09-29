package br.com.planejizze.repository;

import br.com.planejizze.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Receita> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);

    @Query(value = "select coalesce(sum(r.valor), 0) from receita r where r.usuario_id = ?1 and r.status_receita = 0 " +
            "and r.data_recebimento < cast(now() as date) and r.data_recebimento >= cast(now() as date) - 30", nativeQuery = true)
    Double findReceitasLast30Days(Long userId);

    @Query(value = "select coalesce(sum(r.valor), 0) from receita r where r.usuario_id = ?1 and r.status_receita = 1 " +
            "and ((r.data_recebimento > cast(now() as date) and r.data_recebimento <= cast(now() as date) + 30)" +
            " or r.repetir = true)", nativeQuery = true)
    Double findReceitasNext30Days(Long userId);

    @Query(value = "select coalesce(r.valor, 0) from receita r where r.usuario_id = 1 and r.status_receita = 1 " +
            "and (r.data_recebimento > cast(now() as date) or r.repetir = true) order by r.data_recebimento limit 1", nativeQuery = true)
    Double findNextReceita(Long userId);
}

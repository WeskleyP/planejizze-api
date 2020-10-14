package br.com.planejizze.repository;

import br.com.planejizze.model.Despesa;
import br.com.planejizze.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Despesa> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);

    @Query(value = "select coalesce(sum(d.valor), 0) from despesa d where d.usuario_id = ?1 and d.status_despesa = 0 " +
            "and d.data_despesa < cast(now() as date) and d.data_despesa >= cast(now() as date) - 30", nativeQuery = true)
    Double findDespesasLast30Days(Long userId);

    @Query(value = "select coalesce(sum(d.valor), 0) from despesa d where d.usuario_id = ?1 and d.status_despesa = 1 " +
            "and ((d.data_despesa > cast(now() as date) and d.data_despesa <= cast(now() as date) + 30)" +
            " or d.despesa_fixa = true)", nativeQuery = true)
    Double findDespesasNext30Days(Long userId);

    @Query(value = "select coalesce(d.valor, 0) from despesa d where d.usuario_id = 1 and d.status_despesa = 1 " +
            "and (d.data_despesa > cast(now() as date) or d.despesa_fixa = true) order by d.data_despesa limit 1", nativeQuery = true)
    Double findNextDespesa(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', sum(d.valor), 'categoriaId', cr.id, " +
            "'categoriaNome', cr.nome, 'categoriaCor', cr.cor) as text) " +
            "from despesa d " +
            "inner join categoria_despesa cr on cr.id = d.categoria_despesa_id " +
            "where d.status_despesa = 0 and d.data_despesa >  (now() - interval '6 month') " +
            "and d.usuario_id = ?1 group by cr.id", nativeQuery = true)
    List<String> findDespesasLast6Months(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', sum(d.valor), 'categoriaId', cr.id, " +
            "'categoriaNome', cr.nome, 'categoriaCor', cr.cor, 'mes', extract (month from d.data_despesa)) as text) " +
            "from despesa d " +
            "inner join categoria_despesa cr on cr.id = d.categoria_despesa_id " +
            "where extract (month from d.data_despesa) = ?2 " +
            "and d.usuario_id = ?1 group by cr.id, d.data_despesa", nativeQuery = true)
    List<String> findDespesasPorCategoriaEMes(Long userId, Long mes);

    @Query("select d from Despesa d where d.statusDespesa = 1")
    List<Despesa> findAllWhereStatusIsApagar();
}

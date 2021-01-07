package br.com.planejizze.repository;

import br.com.planejizze.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Receita> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Receita> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);

    @Query(value = "select coalesce(sum(trbl.valor_recebido), 0) + coalesce(sum(trml.valor_recebido), 0)" +
            "from receita r " +
            "inner join tipo_recebimento tr on tr.receita_id = r.id " +
            "left join tipo_recebimento_banco trb on trb.receita_id = tr.receita_id " +
            "left join tipo_recebimento_banco_log trbl on trbl.tipo_recebimento_banco_id = trb.receita_id " +
            "left join tipo_recebimento_moeda trm on trm.receita_id = tr.receita_id " +
            "left join tipo_recebimento_moeda_log trml on trml.tipo_recebimento_moeda_id = trm.receita_id " +
            "where r.usuario_id = ?1 and r.ativo" +
            "   and ((trb.receita_id is not null " +
            "   and trbl.tipo_recebimento_banco_id is not null " +
            "   and (trbl.status_receita = 0)" +
            "   and trbl.data_recebimento_real < cast(now() as date) and trbl.data_recebimento_real >= cast(now() as date) - 30)" +
            "or (trm.receita_id is not null " +
            "   and trml.tipo_recebimento_moeda_id is not null " +
            "   and (trml.status_receita = 0)" +
            "   and trml.data_recebimento_real < cast(now() as date) and trml.data_recebimento_real >= cast(now() as date) - 30))",
            nativeQuery = true)
    Double findReceitasLast30Days(Long userId);

    @Query(value = "select coalesce(sum(trbl.valor_recebido), 0) + coalesce(sum(trml.valor_recebido), 0)" +
            "from receita r " +
            "inner join tipo_recebimento tr on tr.receita_id = r.id " +
            "left join tipo_recebimento_banco trb on trb.receita_id = tr.receita_id " +
            "left join tipo_recebimento_banco_log trbl on trbl.tipo_recebimento_banco_id = trb.receita_id " +
            "left join tipo_recebimento_moeda trm on trm.receita_id = tr.receita_id " +
            "left join tipo_recebimento_moeda_log trml on trml.tipo_recebimento_moeda_id = trm.receita_id " +
            "where r.usuario_id = ?1 and r.ativo" +
            "   and ((trb.receita_id is not null " +
            "   and trbl.tipo_recebimento_banco_id is not null " +
            "   and (trbl.status_receita in (1,2))" +
            "   and trbl.data_recebimento_real > cast(now() as date) and trbl.data_recebimento_real <= cast(now() as date) + 30)" +
            "or (trm.receita_id is not null " +
            "   and trml.tipo_recebimento_moeda_id is not null " +
            "   and (trml.status_receita in (1,2))" +
            "   and trml.data_recebimento_real > cast(now() as date) and trml.data_recebimento_real <= cast(now() as date) + 30))", nativeQuery = true)
    Double findReceitasNext30Days(Long userId);

    @Query(value = "select coalesce(r.valor, 0)" +
            "from receita r " +
            "inner join tipo_recebimento tr on tr.receita_id = r.id " +
            "left join tipo_recebimento_banco trb on trb.receita_id = tr.receita_id " +
            "left join tipo_recebimento_banco_log trbl on trbl.tipo_recebimento_banco_id = trb.receita_id " +
            "left join tipo_recebimento_moeda trm on trm.receita_id = tr.receita_id " +
            "left join tipo_recebimento_moeda_log trml on trml.tipo_recebimento_moeda_id = trm.receita_id " +
            "where r.usuario_id = ?1 and r.ativo " +
            "and ((trb.receita_id is not null " +
            "and trbl.tipo_recebimento_banco_id is not null " +
            "and (trbl.status_receita in (1,2))" +
            "and trbl.data_recebimento_experada > cast(now() as date))" +
            "or (trm.receita_id is not null " +
            "and trml.tipo_recebimento_moeda_id is not null " +
            "and (trml.status_receita in (1,2))" +
            "and trml.data_recebimento_experada > cast(now() as date)))" +
            "order by trml.data_recebimento_experada, trbl.data_recebimento_experada limit 1", nativeQuery = true)
    Double findNextReceita(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', (coalesce(sum(trbl.valor_recebido), 0) + coalesce(sum(trml.valor_recebido), 0)), " +
            "'categoriaId', cr.id, 'categoriaNome', cr.nome, 'categoriaCor', cr.cor) as text) " +
            "from receita r " +
            "inner join categoria_receita cr on cr.id = r.categoria_receita_id " +
            "inner join tipo_recebimento tr on tr.receita_id = r.id " +
            "left join tipo_recebimento_banco trb on trb.receita_id = tr.receita_id " +
            "left join tipo_recebimento_banco_log trbl on trbl.tipo_recebimento_banco_id = trb.receita_id " +
            "left join tipo_recebimento_moeda trm on trm.receita_id = tr.receita_id " +
            "left join tipo_recebimento_moeda_log trml on trml.tipo_recebimento_moeda_id = trm.receita_id " +
            "where r.usuario_id = ?1 and r.ativo " +
            "and ((trb.receita_id is not null " +
            "	and trbl.tipo_recebimento_banco_id is not null " +
            "	and (trbl.status_receita = 0)" +
            "	and trbl.data_recebimento_real >=  (now() - interval '6 month'))" +
            "	or (trm.receita_id is not null " +
            "		and trml.tipo_recebimento_moeda_id is not null " +
            "		and (trml.status_receita = 0)" +
            "		and trml.data_recebimento_real >=  (now() - interval '6 month')))" +
            "	group by cr.id", nativeQuery = true)
    List<String> findReceitasLast6Months(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', (coalesce(sum(trbl.valor_recebido), 0) + coalesce(sum(trml.valor_recebido), 0)), " +
            "'categoriaId', cr.id, 'categoriaNome', cr.nome, 'categoriaCor', cr.cor, " +
            "'mes', ?2) as text) " +
            "from receita r " +
            "inner join categoria_receita cr on cr.id = r.categoria_receita_id " +
            "inner join tipo_recebimento tr on tr.receita_id = r.id " +
            "left join tipo_recebimento_banco trb on trb.receita_id = tr.receita_id " +
            "left join tipo_recebimento_banco_log trbl on trbl.tipo_recebimento_banco_id = trb.receita_id " +
            "left join tipo_recebimento_moeda trm on trm.receita_id = tr.receita_id " +
            "left join tipo_recebimento_moeda_log trml on trml.tipo_recebimento_moeda_id = trm.receita_id " +
            "where r.usuario_id = ?1 and r.ativo " +
            "and ((trb.receita_id is not null " +
            "and trbl.tipo_recebimento_banco_id is not null " +
            "	and extract (month from trbl.data_recebimento_experada) = ?2)" +
            "or (trm.receita_id is not null " +
            "	and trml.tipo_recebimento_moeda_id is not null " +
            "	and extract (month from trml.data_recebimento_experada) = ?2 ))" +
            "group by cr.id", nativeQuery = true)
    List<String> findReceitasPorCategoriaEMes(Long userId, Long mes);

    @Query("select distinct r from Receita r " +
            "left join TipoRecebimentoBancoLog trbl on trbl.tipoRecebimentoBanco.id = r.tipoRecebimento.id " +
            "left join TipoRecebimentoMoedaLog trml on trml.tipoRecebimentoMoeda.id = r.tipoRecebimento.id " +
            "where (trbl.statusReceita = 1 or trml.statusReceita = 1)")
    List<Receita> findAllWhereStatusIsAreceber();

    @Query("update TipoRecebimentoBancoLog " +
            "set statusReceita = 0 " +
            "where id = ?1 ")
    @Transactional
    @Modifying
    Integer updateReceitaStatusBanco(Long id);

    @Query("update TipoRecebimentoMoedaLog " +
            "set statusReceita = 0 " +
            "where id = ?1 ")
    @Transactional
    @Modifying
    Integer updateReceitaStatusMoeda(Long id);

    @Query(value = "select distinct r from Receita r " +
            "left join TipoRecebimentoBancoLog trbl on trbl.tipoRecebimentoBanco.id = r.tipoRecebimento.id " +
            "left join TipoRecebimentoMoedaLog trml on trml.tipoRecebimentoMoeda.id = r.tipoRecebimento.id " +
            "where (trml.dataRecebimentoReal >= ?2 or trbl.dataRecebimentoReal >= ?2)" +
            "and r.usuario.id = ?1")
    List<Receita> findReceitasForDashboard(Long userId, Date date);
}

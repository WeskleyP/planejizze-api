package br.com.planejizze.repository;

import br.com.planejizze.model.Despesa;
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
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Despesa> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Optional<Despesa> findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);

    @Query(value = "select coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)" +
            "from despesa d " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo" +
            "   and ((tpc.despesa_id is not null " +
            "   and tpcp.tipo_pagamento_cartao_id is not null " +
            "   and (tpcp.status_despesa = 0)" +
            "   and tpcp.data_pagamento_real < cast(now() as date) and tpcp.data_pagamento_real >= cast(now() as date) - 30)" +
            "or (tpm.despesa_id is not null " +
            "   and tpml.tipo_pagamento_moeda_id is not null " +
            "   and (tpml.status_despesa = 0)" +
            "   and tpml.data_pagamento_real < cast(now() as date) and tpml.data_pagamento_real >= cast(now() as date) - 30))",
            nativeQuery = true)
    Double findDespesasLast30Days(Long userId);

    @Query(value = "select coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)" +
            "from despesa d " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo" +
            "   and ((tpc.despesa_id is not null " +
            "   and tpcp.tipo_pagamento_cartao_id is not null " +
            "   and (tpcp.status_despesa in (1,2))" +
            "   and tpcp.data_pagamento_real > cast(now() as date) and tpcp.data_pagamento_real <= cast(now() as date) + 30)" +
            "or (tpm.despesa_id is not null " +
            "   and tpml.tipo_pagamento_moeda_id is not null " +
            "   and (tpml.status_despesa in (1,2))" +
            "   and tpml.data_pagamento_real > cast(now() as date) and tpml.data_pagamento_real <= cast(now() as date) + 30))", nativeQuery = true)
    Double findDespesasNext30Days(Long userId);

    @Query(value = "select coalesce(d.valor, 0)" +
            "from despesa d " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo " +
            "and ((tpc.despesa_id is not null " +
            "and tpcp.tipo_pagamento_cartao_id is not null " +
            "and (tpcp.status_despesa in (1,2))" +
            "and tpcp.data_pagamento_experada > cast(now() as date))" +
            "or (tpm.despesa_id is not null " +
            "and tpml.tipo_pagamento_moeda_id is not null " +
            "and (tpml.status_despesa in (1,2))" +
            "and tpml.data_pagamento_experada > cast(now() as date)))" +
            "order by tpml.data_pagamento_experada, tpcp.data_pagamento_experada limit 1", nativeQuery = true)
    Double findNextDespesa(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', (coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)), " +
            "'categoriaId', cd.id, 'categoriaNome', cd.nome, 'categoriaCor', cd.cor) as text) " +
            "from despesa d " +
            "inner join categoria_despesa cd on cd.id = d.categoria_despesa_id " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo " +
            "and ((tpc.despesa_id is not null " +
            "	and tpcp.tipo_pagamento_cartao_id is not null " +
            "	and (tpcp.status_despesa = 0)" +
            "	and tpcp.data_pagamento_real >=  (now() - interval '6 month'))" +
            "	or (tpm.despesa_id is not null " +
            "		and tpml.tipo_pagamento_moeda_id is not null " +
            "		and (tpml.status_despesa = 0)" +
            "		and tpml.data_pagamento_real >=  (now() - interval '6 month')))" +
            "	group by cd.id", nativeQuery = true)
    List<String> findDespesasLast6Months(Long userId);

    @Query(value = "select cast(jsonb_build_object('valor', (coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)), " +
            "'categoriaId', cd.id, 'categoriaNome', cd.nome, 'categoriaCor', cd.cor, " +
            "'mes', ?2) as text) " +
            "from despesa d " +
            "inner join categoria_despesa cd on cd.id = d.categoria_despesa_id " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo " +
            "and (tpc.despesa_id is not null and tpc.cartao_id = ?3 " +
            "and tpcp.tipo_pagamento_cartao_id is not null " +
            "	and extract (month from tpcp.data_pagamento_experada) = ?2)" +
            "group by cd.id", nativeQuery = true)
    List<String> findDespesasPorCategoriaEMesECartao(Long userId, Long mes, Long cartao);

    @Query("select distinct d from Despesa d " +
            "left join TipoPagamentoCartaoParcelas tpcp on tpcp.tipoPagamentoCartao.id = d.tipoPagamento.id " +
            "left join TipoPagamentoMoedaLog tpml on tpml.tipoPagamentoMoeda.id = d.tipoPagamento.id " +
            "where (tpcp.statusDespesa = 1 or tpml.statusDespesa = 1)")
    List<Despesa> findAllWhereStatusIsApagar();

    @Query("update TipoPagamentoMoedaLog " +
            "set statusDespesa = 0," +
            "   dataPagamentoReal = current_date " +
            "where id = ?1 ")
    @Transactional
    @Modifying
    Integer updateDespesaStatusMoeda(Long id);

    @Query("update TipoPagamentoCartaoParcelas " +
            "set statusDespesa = 0," +
            "   dataPagamentoReal = current_date " +
            "where id = ?1 ")
    @Transactional
    @Modifying
    Integer updateDespesaStatusCartao(Long id);

    @Query("select distinct d from Despesa d " +
            "left join TipoPagamentoCartaoParcelas tpcp on tpcp.tipoPagamentoCartao.id = d.tipoPagamento.id " +
            "left join TipoPagamentoMoedaLog tpml on tpml.tipoPagamentoMoeda.id = d.tipoPagamento.id " +
            "where (tpml.dataPagamentoReal >= ?2 or tpcp.dataPagamentoReal >= ?2)" +
            "and d.usuario.id = ?1")
    List<Despesa> findDespesasForDashboard(Long userId, Date date);

    @Query(value = "select cast(jsonb_build_object('valor', (coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)), " +
            "'categoriaId', cd.id, 'categoriaNome', cd.nome, 'categoriaCor', cd.cor, " +
            "'mes', ?2) as text) " +
            "from despesa d " +
            "inner join categoria_despesa cd on cd.id = d.categoria_despesa_id " +
            "inner join tipo_pagamento tp on tp.despesa_id = d.id " +
            "left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "where d.usuario_id = ?1 and d.ativo " +
            "and ((tpc.despesa_id is not null " +
            "and tpcp.tipo_pagamento_cartao_id is not null " +
            "	and extract (month from tpcp.data_pagamento_experada) = ?2)" +
            "or (tpm.despesa_id is not null " +
            "	and tpml.tipo_pagamento_moeda_id is not null " +
            "	and extract (month from tpml.data_pagamento_experada) = ?2 ))" +
            "group by cd.id", nativeQuery = true)
    List<String> findDespesasPorCategoriaEMes(Long userId, Long mes);
}

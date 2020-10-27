package br.com.planejizze.repository;

import br.com.planejizze.dto.PlanejamentoDropdownDTO;
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
            "or (p.dataInicio >= ?2 and p.dataFim <= ?3 )) and p.ativo = true")
    Long findPlanejamentoIdFromDate(Long usuarioId, Date dataInicio, Date dataFim);

    @Query(value = "select new br.com.planejizze.dto.PlanejamentoDropdownDTO(p.id, p.descricao, p.dataInicio, p.dataFim) " +
            "from Planejamento p where p.usuario.id = ?1")
    List<PlanejamentoDropdownDTO> findAllDropdown(Long userId);

    @Query(value = "select cast(jsonb_build_object('id', p.id, 'descricao', p.descricao," +
            "'metaGastos',p.meta_gastos,'alertaPorcentagem', p.alerta_porcentagem, 'dataInicio', p.data_inicio," +
            "'dataFim', p.data_fim,'planejamentoPrevistoRealCategorias', jsonb_agg(jsonb_build_object('categoriaId'," +
            "cd.id ,'categoriaNome',cd.nome, 'valorMaximoGasto', pc.valor_max_gasto," +
            "'valorGastoAtual', des.total ))) as text) " +
            "from planejamento p " +
            "left join  planejamento_categoria pc on pc.planejamento_id = p.id " +
            "left join categoria_despesa cd on cd.id = pc.categoria_id " +
            "left join lateral (select d.categoria_despesa_id as catId , " +
            "   (coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)) as total " +
            "	from despesa d " +
            "	left join tipo_pagamento tp on tp.despesa_id = d.id " +
            "	left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "	left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "	left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "	left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "	where d.usuario_id = ?2 " +
            "	and d.ativo group by d.categoria_despesa_id)as des on des.catId = cd.id " +
            "where p.id = ?1 and p.ativo " +
            "and p.usuario_id = ?2 " +
            "group by p.id", nativeQuery = true)
    String findPlanejamentoPrevistoDespesaReal(Long planejamentoId, Long userId);

    @Query(value = "select cast(jsonb_build_object('id', p.id, 'descricao', p.descricao," +
            "'metaGastos',p.meta_gastos,'alertaPorcentagem', p.alerta_porcentagem, 'dataInicio', p.data_inicio," +
            "'dataFim', p.data_fim,'planejamentoPrevistoRealCategorias', jsonb_agg(jsonb_build_object('categoriaId'," +
            "cd.id ,'categoriaNome',cd.nome, 'valorMaximoGasto', pc.valor_max_gasto," +
            "'valorGastoAtual', des.total ))) as text) " +
            "from planejamento p " +
            "left join  planejamento_categoria pc on pc.planejamento_id = p.id " +
            "left join categoria_despesa cd on cd.id = pc.categoria_id " +
            "left join lateral (select d.categoria_despesa_id as catId , " +
            "   (coalesce(sum(tpcp.valor_parcela), 0) + coalesce(sum(tpml.valor_pagamento), 0)) as total " +
            "	from despesa d " +
            "	left join tipo_pagamento tp on tp.despesa_id = d.id " +
            "	left join tipo_pagamento_cartao tpc on tpc.despesa_id = tp.despesa_id " +
            "	left join tipo_pagamento_cartao_parcelas tpcp on tpcp.tipo_pagamento_cartao_id = tpc.despesa_id " +
            "	left join tipo_pagamento_moeda tpm on tpm.despesa_id = tp.despesa_id " +
            "	left join tipo_pagamento_moeda_log tpml on tpml.tipo_pagamento_moeda_id = tpm.despesa_id " +
            "	where d.usuario_id = ?1 " +
            "	and d.ativo group by d.categoria_despesa_id) as des on des.catId = cd.id " +
            "where p.ativo " +
            "and p.usuario_id = ?1 " +
            "group by p.id order by p.data_fim desc limit 1", nativeQuery = true)
    String findLastPlanejamento(Long userId);
}

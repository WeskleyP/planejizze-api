package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tipo_pagamento_cartao")
@Entity
@JsonTypeName("pagamentoComCartao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPagamentoCartao extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @Column(name = "quantidade_parcelas")
    private Long quantidadeParcelas;
    @ManyToOne
    @JoinColumn(name = "cartao_id", foreignKey = @ForeignKey(name = "tipo_pagamento_cartao_cartao_fkey"))
    private Cartao cartao;
}

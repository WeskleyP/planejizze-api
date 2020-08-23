package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@Entity
@JsonTypeName("pagamentoComCartao")
public class TipoPagamentoCartao extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @Column(name = "quantidade_parcelas")
    private Long quantidadeParcelas;
    @ManyToOne
    @JoinColumn(name = "cartao_id", foreignKey = @ForeignKey(name = "tipo_pagamento_cartao_cartao_fkey"))
    private Cartao cartao;
}

package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonTypeName("pagamentoComCartao")
public class TipoPagamentoCartao extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @Column(name = "quantidade_parcelas")
    private Long quantidadeParcelas;
    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}

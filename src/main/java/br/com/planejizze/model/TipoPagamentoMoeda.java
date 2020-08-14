package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComDinheiro")
public class TipoPagamentoMoeda extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @Column(name = "moeda")
    private String moeda;
}

package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tipo_pagamento_dinheiro")
@Entity
@JsonTypeName("pagamentoComDinheiro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPagamentoMoeda extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @Column(name = "moeda")
    private String moeda;
}

package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    @Column(name = "dia_pagamento")
    private String diaPagamento;
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "tipoPagamentoMoeda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TipoPagamentoMoedaLog> tipoPagamentoMoedaLogs = new ArrayList<>();
}

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
@Table(name = "tipo_pagamento_cartao")
@Entity
@JsonTypeName("pagamentoComCartao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPagamentoCartao extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @ManyToOne
    @JoinColumn(name = "cartao_id", foreignKey = @ForeignKey(name = "tipo_pagamento_cartao_cartao_fkey"))
    private Cartao cartao;
    @Fetch(value = FetchMode.SELECT)
    @OneToMany(mappedBy = "tipoPagamentoCartao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TipoPagamentoCartaoParcelas> tipoPagamentoCartaoParcelas = new ArrayList<>();
}

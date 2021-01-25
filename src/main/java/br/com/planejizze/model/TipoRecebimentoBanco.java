package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "tipo_recebimento_banco")
@Entity
@JsonTypeName("recebimentoComBanco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoRecebimentoBanco extends TipoRecebimento {
    private static final long serialVersionUID = -6991733856092034445L;

    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = false, foreignKey = @ForeignKey(name = "tipo_recebimento_banco_banco_fkey"))
    private Banco banco;
    @Column(name = "dia_pagamento")
    private String diaPagamento;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "tipoRecebimentoBanco", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<TipoRecebimentoBancoLog> tipoRecebimentoBancoLogs = new ArrayList<>();
}

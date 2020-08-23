package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
public abstract class TipoPagamento implements Serializable {
    private static final long serialVersionUID = -118387155139744055L;

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "despesa_id", foreignKey =  @ForeignKey(name = "tipo_pagamento_despesa_fkey"))
    @MapsId
    private Despesa despesa;

}

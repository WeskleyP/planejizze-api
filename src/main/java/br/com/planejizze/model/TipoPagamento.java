package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tipo_pagamento")
@EqualsAndHashCode
@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class TipoPagamento implements Serializable {
    private static final long serialVersionUID = -118387155139744055L;

    @Id
    @Column(name = "despesa_id")
    @GeneratedValue(generator = "gen2")
    @GenericGenerator(name = "gen2", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "despesa"))
    private Long id;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Despesa despesa;

}

package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonTypeName("recebimentoComBanco")
public class TipoRecebimentoBanco extends TipoPagamento {
    private static final long serialVersionUID = -6991733856092034445L;

    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = false, foreignKey = @ForeignKey(name = "tipo_recebimento_banco_banco_fkey"))
    private Banco banco;
}

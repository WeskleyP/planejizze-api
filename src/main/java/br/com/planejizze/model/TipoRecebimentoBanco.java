package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}

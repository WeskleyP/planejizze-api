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
public abstract class TipoRecebimento implements Serializable {
    private static final long serialVersionUID = -882951924420469319L;
    @Id
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "receita_id", foreignKey = @ForeignKey(name = "tipo_recebimento_receita_fkey"))
    @MapsId
    private Receita receita;
}

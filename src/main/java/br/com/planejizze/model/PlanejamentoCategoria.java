package br.com.planejizze.model;

import br.com.planejizze.model.pk.PlanejamentoCategoriaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "planejamento_categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoCategoria {

    @EmbeddedId
    private PlanejamentoCategoriaPK planejamentoCategoriaPK;
    @Column(name = "valor_max_gasto")
    private Double valorMaximoGasto;
}

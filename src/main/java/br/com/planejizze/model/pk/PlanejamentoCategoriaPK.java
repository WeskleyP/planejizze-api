package br.com.planejizze.model.pk;

import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.model.Planejamento;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoCategoriaPK implements Serializable {

    private static final long serialVersionUID = 1528870034966627851L;
    @ManyToOne
    @JoinColumn(name = "planejamento_id", foreignKey = @ForeignKey(name = "planejamento_categoria_planejamento_fkey"))
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Planejamento planejamento;
    @ManyToOne
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "planejamento_categoria_categoria_despesa_fkey"))
    private CategoriaDespesa categoriaDespesa;
}

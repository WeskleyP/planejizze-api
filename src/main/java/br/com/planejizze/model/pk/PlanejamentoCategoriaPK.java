package br.com.planejizze.model.pk;

import br.com.planejizze.model.CategoriaPlanejamento;
import br.com.planejizze.model.Planejamento;
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
public class PlanejamentoCategoriaPK implements Serializable {

    private static final long serialVersionUID = 1528870034966627851L;
    @ManyToOne
    @JoinColumn(name = "planejamento_id", foreignKey = @ForeignKey(name = "planejamento_categoria_planejamento_fkey"))
    private Planejamento planejamento;
    @ManyToOne
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "planejamento_categoria_categoria_planejamento_fkey"))
    private CategoriaPlanejamento categoriaPlanejamento;
}

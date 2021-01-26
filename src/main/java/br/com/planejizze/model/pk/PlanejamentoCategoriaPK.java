package br.com.planejizze.model.pk;

import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.model.Planejamento;
import br.com.planejizze.utils.serializer.PlanejamentoSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoCategoriaPK implements Serializable {

    private static final long serialVersionUID = 1528870034966627851L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planejamento_id", foreignKey = @ForeignKey(name = "planejamento_categoria_planejamento_fkey"))
    @JsonSerialize(using = PlanejamentoSerializer.class)
    private Planejamento planejamento;
    @ManyToOne
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "planejamento_categoria_categoria_despesa_fkey"))
    private CategoriaDespesa categoriaDespesa;
}

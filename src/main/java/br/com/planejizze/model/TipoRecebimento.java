package br.com.planejizze.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tipo_recebimento")
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class TipoRecebimento implements Serializable {
    private static final long serialVersionUID = -882951924420469319L;
    @Id
    @Column(name = "receita_id")
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "receita"))
    private Long id;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Receita receita;
}

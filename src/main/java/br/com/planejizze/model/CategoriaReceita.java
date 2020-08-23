package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria_receita")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categoria_receita SET active = false WHERE id = ?")
@Where(clause = Constants.ATIVO)
@SequenceGenerator(name = "categoria_receita_sequence", sequenceName = "categoria_receita_sequence_pkey", initialValue = 30, allocationSize = 1)
public class CategoriaReceita {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_receita_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "nome")
    @NotBlank(message = "O nome deve ser informado!")
    @NotNull(message = "O nome não dever ser nulo!")
    private String nome;
    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "categoria_receita_usuario_fkey"))
    private Usuario usuario;
}

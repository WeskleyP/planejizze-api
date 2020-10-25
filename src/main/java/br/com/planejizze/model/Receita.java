package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "receita")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE receita SET ativo = false WHERE id = ?")
@SequenceGenerator(name = "receita_sequence", sequenceName = "receita_sequence_pkey", allocationSize = 1)
@Where(clause = Constants.ATIVO)
@JsonIgnoreProperties({"createdOn", "updatedOn"})
public class Receita {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receita_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor")
    @NotNull(message = "O valor não dever ser nulo!")
    private Double valor;
    @Column(name = "repetir", columnDefinition = "boolean default false")
    @NotNull(message = "A opção de repetir não dever ser nulo!")
    private Boolean repetir;
    @JsonIgnore
    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativo = true;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @OneToOne(mappedBy = "receita", cascade = CascadeType.ALL)
    private TipoRecebimento tipoRecebimento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "receita_usuario_fkey"))
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "categoria_receita_id", nullable = false, foreignKey = @ForeignKey(name = "receita_categoria_receita_fkey"))
    private CategoriaReceita categoriaReceita;
}

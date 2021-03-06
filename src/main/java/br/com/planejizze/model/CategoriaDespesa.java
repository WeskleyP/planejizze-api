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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria_despesa")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categoria_despesa SET ativo = false WHERE id = ?")
@Where(clause = Constants.ATIVO)
@SequenceGenerator(name = "categoria_despesa_sequence", sequenceName = "categoria_despesa_sequence_pkey", initialValue = 30, allocationSize = 1)
@JsonIgnoreProperties({"createdOn", "updatedOn"})
public class CategoriaDespesa {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_despesa_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "nome")
    @NotBlank(message = "O nome deve ser informado!")
    @NotNull(message = "O nome não dever ser nulo!")
    private String nome;
    @Column(name = "cor")
    @Pattern(regexp = "[#-0-9a-fA-F]+", message = "Cor inváilida!")
    private String cor;
    @JsonIgnore
    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativo = true;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "categoria_despesa_usuario_fkey"))
    private Usuario usuario;

}

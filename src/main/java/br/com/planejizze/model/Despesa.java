package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "despesa")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE despesa SET ativo = false WHERE id = ?")
@SequenceGenerator(name = "despesa_sequence", sequenceName = "despesa_sequence_pkey", allocationSize = 1)
@Where(clause = Constants.ATIVO)
public class Despesa {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "despesa_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor")
    @NotNull(message = "O valor não dever ser nulo!")
    private Double valor;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_despesa")
    private Date dataDespesa;
    @Column(name = "despesa_fixa", columnDefinition = "boolean default false")
    @NotNull(message = "A opção de despesa fixa não dever ser nula!")
    private Boolean despesaFixa;
    @JsonIgnore
    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativo = true;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "despesa")
    private TipoPagamento tipoPagamento;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "despesa_usuario_fkey"))
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "categoria_despesa_id", nullable = false, foreignKey = @ForeignKey(name = "despesa_categoria_despesa_fkey"))
    private CategoriaDespesa categoriaDespesa;

}

package br.com.planejizze.model;

import br.com.planejizze.enums.StatusDespesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "despesa")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "despesa_sequence", sequenceName = "despesa_sequence_pkey", allocationSize = 1)
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_despesa")
    private StatusDespesa statusDespesa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

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

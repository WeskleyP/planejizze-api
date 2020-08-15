package br.com.planejizze.model;

import br.com.planejizze.enums.StatusDespesa;
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
public class Despesa {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor")
    @NotBlank(message = "O valor deve ser informado!")
    @NotNull(message = "O valor não dever ser nulo!")
    private Double valor;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_despesa")
    private Date dataDespesa;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_despesa")
    private StatusDespesa statusDespesa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "despesa")
    private TipoPagamento tipoPagamento;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "categoria_despesa_id", nullable = false)
    private CategoriaDespesa categoriaDespesa;

}
package br.com.planejizze.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "planejamento")
@Data
@NoArgsConstructor
public class Planejamento {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "alerta_porcentagem")
    private Long alertaPorcentagem;
    @Column(name = "receita_total")
    @NotBlank(message = "A receita total deve ser informado!")
    @NotNull(message = "A receita total não dever ser nulo!")
    private Double receitaTotal;
    @Column(name = "meta_gastos")
    @NotBlank(message = "A meta de gastos deve ser informado!")
    @NotNull(message = "A meta de gastos não dever ser nulo!")
    private Double metaGastos;
    @NotBlank(message = "A data inicial deve ser informado!")
    @NotNull(message = "A data inicial não dever ser nulo!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inicio")
    private Date dataInicio;
    @NotBlank(message = "A data final deve ser informado!")
    @NotNull(message = "A data final não dever ser nulo!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_fim")
    private Date dataFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(mappedBy = "planejamentoCategoriaPK.planejamento", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PlanejamentoCategoria> categorias;

}

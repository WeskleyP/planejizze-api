package br.com.planejizze.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "planejamento")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "planejamento_sequence", sequenceName = "planejamento_sequence_pkey", allocationSize = 1)
public class Planejamento {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planejamento_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "alerta_porcentagem")
    private Long alertaPorcentagem;
    @Column(name = "receita_total")
    @NotNull(message = "A receita total não dever ser nulo!")
    private Double receitaTotal;
    @Column(name = "meta_gastos")
    @NotNull(message = "A meta de gastos não dever ser nulo!")
    private Double metaGastos;
    @NotNull(message = "A data inicial não dever ser nulo!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date dataInicio;
    @NotNull(message = "A data final não dever ser nulo!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim")
    private Date dataFim;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "planejamento_usuario_fkey"))
    private Usuario usuario;
    @OneToMany(mappedBy = "planejamentoCategoriaPK.planejamento", fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    private List<PlanejamentoCategoria> categorias;

    public Planejamento(Long id, String descricao, Long alertaPorcentagem,
                        @NotNull(message = "A receita total não dever ser nulo!") Double receitaTotal,
                        @NotNull(message = "A meta de gastos não dever ser nulo!") Double metaGastos,
                        @NotNull(message = "A data inicial não dever ser nulo!") Date dataInicio,
                        @NotNull(message = "A data final não dever ser nulo!") Date dataFim, Usuario usuario) {
        this.id = id;
        this.descricao = descricao;
        this.alertaPorcentagem = alertaPorcentagem;
        this.receitaTotal = receitaTotal;
        this.metaGastos = metaGastos;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuario = usuario;
    }
}

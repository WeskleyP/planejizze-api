package br.com.planejizze.model;

import br.com.planejizze.enums.StatusDespesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tipo_pagamento_cartao_parcelas")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "tipo_pagamento_cartao_parcelas_sequence",
        sequenceName = "tipo_pagamento_cartao_parcelas_sequence_pkey", allocationSize = 1)
public class TipoPagamentoCartaoParcelas {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_pagamento_cartao_parcelas_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tipo_pagamento_cartao_id", foreignKey = @ForeignKey(name = "tipo_pagamento_moeda_fkey"), nullable = false)
    private TipoPagamentoCartao tipoPagamentoCartao;
    @NotNull(message = "O número da parcela não dever ser nulo!")
    @Column(name = "numero_parcela", nullable = false)
    private Long numeroParcela;
    @NotNull(message = "O valor da parcela não dever ser nulo!")
    @Column(name = "valor_parcela", nullable = false)
    private Double valorParcela;
    @NotNull(message = "O status da despesa não dever ser nulo!")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_despesa", nullable = false)
    private StatusDespesa statusDespesa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento_real")
    private Date dataPagamentoReal;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento_experada")
    private Date dataPagamentoExperada;
}

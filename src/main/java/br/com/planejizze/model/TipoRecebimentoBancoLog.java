package br.com.planejizze.model;

import br.com.planejizze.enums.StatusReceita;
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
@Table(name = "tipo_recebimento_banco_log")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "tipo_recebimento_banco_log_sequence",
        sequenceName = "tipo_recebimento_banco_log_sequence_pkey", allocationSize = 1)
public class TipoRecebimentoBancoLog {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_recebimento_banco_log_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tipo_recebimento_banco_id",
            foreignKey = @ForeignKey(name = "tipo_recebimento_banco_fkey"), nullable = false)
    private TipoRecebimentoBanco tipoRecebimentoBanco;
    @NotNull(message = "O valor recebido não dever ser nulo!")
    @Column(name = "valor_recebido", nullable = false)
    private Double valorRecebido;
    @NotNull(message = "O status da receita não dever ser nulo!")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_receita", nullable = false)
    private StatusReceita statusReceita;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_recebimento_experada")
    private Date dataRecebimentoExperada;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_recebimento_real")
    private Date dataRecebimentoReal;
}

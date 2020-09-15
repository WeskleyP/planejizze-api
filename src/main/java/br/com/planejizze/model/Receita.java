package br.com.planejizze.model;

import br.com.planejizze.enums.StatusReceita;
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
@Table(name = "receita")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "receita_sequence", sequenceName = "receita_sequence_pkey", allocationSize = 1)
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_receita")
    private StatusReceita statusDespesa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_recebimento")
    private Date dataRecebimento;
    @Column(name = "repetir", columnDefinition = "boolean default false")
    @NotNull(message = "A opção de repetir não dever ser nulo!")
    private Boolean repetir;

    @OneToOne(mappedBy = "receita", cascade = CascadeType.ALL)
    private TipoRecebimento tipoRecebimento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "receita_usuario_fkey"))
    private Usuario usuario;
}

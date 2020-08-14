package br.com.planejizze.model;

import br.com.planejizze.enums.StatusReceita;
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
public class Receita {

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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_receita")
    private StatusReceita statusDespesa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_recebimento")
    private Date dataRecebimento;
    @Column(name = "repetir", columnDefinition = "boolean default false")
    @NotBlank(message = "A opção de repetir deve ser informado!")
    @NotNull(message = "A opção de repetir não dever ser nulo!")
    private Boolean repetir;

    @OneToOne(mappedBy = "receita", cascade = CascadeType.ALL)
    private TipoRecebimento tipoRecebimento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}

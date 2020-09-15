package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cartao")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "cartao_sequence", sequenceName = "cartao_sequence_pkey", allocationSize = 1)
public class Cartao {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartao_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "nome")
    @NotBlank(message = "O nome deve ser informado!")
    @NotNull(message = "O nome não dever ser nulo!")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "bandeira")
    private String bandeira;
    @Column(name = "limite")
    private Double limite;
    @Column(name = "empresa_cartao")
    private String empresaCartao;
    @DateTimeFormat(pattern = "dd/MM")
    @JsonFormat(pattern = "dd/MM")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "cartao_usuario_fkey"))
    private Usuario usuario;
}

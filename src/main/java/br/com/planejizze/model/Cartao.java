package br.com.planejizze.model;

import br.com.planejizze.enums.TipoCartao;
import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cartao")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cartao SET ativo = false WHERE id = ?")
@SequenceGenerator(name = "cartao_sequence", sequenceName = "cartao_sequence_pkey", allocationSize = 1)
@Where(clause = Constants.ATIVO)
@JsonIgnoreProperties({"createdOn", "updatedOn"})
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
    @DateTimeFormat(pattern = "dd")
    @JsonFormat(pattern = "dd")
    @Column(name = "data_vencimento")
    private String dataVencimento;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_cartao")
    private TipoCartao tipoCartao;
    @JsonIgnore
    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativo = true;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "cartao_usuario_fkey"))
    private Usuario usuario;
}

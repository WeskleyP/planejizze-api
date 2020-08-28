package br.com.planejizze.model;

import br.com.planejizze.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "planejamento")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "banco_sequence", sequenceName = "banco_sequence_pkey", allocationSize = 1)
public class Banco {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banco_sequence")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "banco")
    private String banco;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "banco_usuario_fkey"))
    private Usuario usuario;

}

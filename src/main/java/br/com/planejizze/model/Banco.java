package br.com.planejizze.model;

import br.com.planejizze.enums.TipoConta;
import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "banco")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE banco SET ativo = false WHERE id = ?")
@SequenceGenerator(name = "banco_sequence", sequenceName = "banco_sequence_pkey", allocationSize = 1)
@Where(clause = Constants.ATIVO)
@JsonIgnoreProperties({"createdOn", "updatedOn"})
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
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "banco_usuario_fkey"))
    private Usuario usuario;

}

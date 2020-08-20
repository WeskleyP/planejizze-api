package br.com.planejizze.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
public class Role {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "nome")
    @NotBlank(message = "O nome deve ser informado!")
    @NotNull(message = "O nome não dever ser nulo!")
    private String nome;
    @Type(type = "jsonb")
    @Column(name = "permissions", columnDefinition = "jsonb")
    private Object permissions;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios = new ArrayList<>();
}

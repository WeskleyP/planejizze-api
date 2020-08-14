package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE usuario SET active = false WHERE id = ?")
@Where(clause = Constants.ATIVO)
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "nome")
    @NotBlank(message = "O nome deve ser informado!")
    @NotNull(message = "O nome não dever ser nulo!")
    private String nome;
    @Column(name = "sobrenome")
    @NotBlank(message = "O sobrenome deve ser informado!")
    @NotNull(message = "O sobrenome não deve ser nulo!")
    private String sobrenome;
    @Column(name = "username")
    @NotBlank(message = "O username deve ser informado!")
    @NotNull(message = "O username não deve ser nulo!")
    private String username;
    @Column(name = "nome")
    @Email(message = "Digite um email válido")
    @NotBlank(message = "O email deve ser informado!")
    @NotNull(message = "O email não deve ser nulo!")
    private String email;
    @Column(name = "email_verified")
    private Boolean emailVerified;
    @Column(name = "senha")
    @NotBlank(message = "A senha deve ser informado!")
    @NotNull(message = "A senha não deve ser nula!")
    private String senha;
    @Transient
    private String contraSenha;
    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private Boolean isActive;
}

package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE usuario SET ativo = false WHERE id = ?")
@Where(clause = Constants.ATIVO)
@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence_pkey", allocationSize = 1)
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 8815680579773416488L;
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
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
    @Column(name = "email")
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
    @JsonIgnore
    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativo = true;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_role_usuario_fkey")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "usuario_role_role_fkey")))
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome().toUpperCase()))
                .collect(toList());
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}

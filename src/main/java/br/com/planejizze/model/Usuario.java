package br.com.planejizze.model;

import br.com.planejizze.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE usuario SET active = false WHERE id = ?")
@Where(clause = Constants.ATIVO)
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 8815680579773416488L;
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
    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private Boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome().toUpperCase()))
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
        return this.isActive;
    }
}

package br.com.planejizze.dto;

import br.com.planejizze.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private List<Role> roles;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdOn;

    public UsuarioDTO(Long id, String username, String nome, String sobrenome) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }
}

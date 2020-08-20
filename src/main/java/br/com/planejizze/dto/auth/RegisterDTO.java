package br.com.planejizze.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String nome;
    private String sobrenome;
    private String username;
    private String email;
    private String senha;
    private String senhaConfirmação;
}

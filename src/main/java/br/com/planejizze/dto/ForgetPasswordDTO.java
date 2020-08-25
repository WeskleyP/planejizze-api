package br.com.planejizze.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgetPasswordDTO {

    private String senha;
    private String contraSenha;
}

package br.com.planejizze.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoriaDespesaDTO {

    private Long id;
    private String nome;
    private String cor;
}

package br.com.planejizze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaPorCategoriaDTO {

    private Double valor;
    private Long categoriaId;
    private String categoriaNome;
    private String categoriaCor;
    private Long mes;
}

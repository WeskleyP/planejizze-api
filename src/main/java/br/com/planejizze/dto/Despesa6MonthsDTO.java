package br.com.planejizze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despesa6MonthsDTO {

    private Double valor;
    private Long categoriaId;
    private String categoriaNome;
    private String categoriaCor;
}

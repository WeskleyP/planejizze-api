package br.com.planejizze.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoDropdownDTO {

    private Long id;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
}

package br.com.planejizze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoPrevistoRealCategoriasDTO {

    private Long categoriaId;
    private String categoriaNome;
    private Double valorMaximoGasto;
    private Double valorGastoAtual;
}

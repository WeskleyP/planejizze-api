package br.com.planejizze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoPrevistoRealDTO {

    private Long id;
    private String descricao;
    private Double metaGastos;
    private Long alertaPorcentagem;
    private Date dataInicio;
    private Date dataFim;
    private List<PlanejamentoPrevistoRealCategoriasDTO> planejamentoPrevistoRealCategorias = new ArrayList<>();
}

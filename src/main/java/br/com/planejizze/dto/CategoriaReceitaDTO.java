package br.com.planejizze.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class CategoriaReceitaDTO extends RepresentationModel<CategoriaReceitaDTO> {

    private Long id;
    private String nome;
}

package br.com.planejizze.converters;

import br.com.planejizze.dto.CategoriaDespesaDTO;
import br.com.planejizze.model.CategoriaDespesa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaDespesaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaDespesaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoriaDespesaDTO convertToDTO(CategoriaDespesa categoriaDespesa) {
        return modelMapper.map(categoriaDespesa, CategoriaDespesaDTO.class);
    }

    public CategoriaDespesa convertToCategoriaDespesa(CategoriaDespesaDTO categoriaDespesaDTO) {
        return modelMapper.map(categoriaDespesaDTO, CategoriaDespesa.class);
    }

    public List<CategoriaDespesaDTO> convertListToDTO(List<CategoriaDespesa> categoriaDespesas) {
        return categoriaDespesas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

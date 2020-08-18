package br.com.planejizze.converters;

import br.com.planejizze.dto.CategoriaPlanejamentoDTO;
import br.com.planejizze.model.CategoriaPlanejamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaPlanejamentoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaPlanejamentoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoriaPlanejamentoDTO convertToDTO(CategoriaPlanejamento categoriaPlanejamento) {
        return modelMapper.map(categoriaPlanejamento, CategoriaPlanejamentoDTO.class);
    }

    public CategoriaPlanejamento convertToCategoriaRceita(CategoriaPlanejamentoDTO categoriaPlanejamentoDTO) {
        return modelMapper.map(categoriaPlanejamentoDTO, CategoriaPlanejamento.class);
    }

    public List<CategoriaPlanejamentoDTO> convertListToDTO(List<CategoriaPlanejamento> categoriaPlanejamentos) {
        return categoriaPlanejamentos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

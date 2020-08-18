package br.com.planejizze.converters;

import br.com.planejizze.dto.CategoriaReceitaDTO;
import br.com.planejizze.model.CategoriaReceita;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaReceitaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaReceitaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoriaReceitaDTO convertToDTO(CategoriaReceita categoriaReceita) {
        return modelMapper.map(categoriaReceita, CategoriaReceitaDTO.class);
    }

    public CategoriaReceita convertToCategoriaRceita(CategoriaReceitaDTO categoriaReceitaDTO) {
        return modelMapper.map(categoriaReceitaDTO, CategoriaReceita.class);
    }

    public List<CategoriaReceitaDTO> convertListToDTO(List<CategoriaReceita> categoriaReceitas) {
        return categoriaReceitas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

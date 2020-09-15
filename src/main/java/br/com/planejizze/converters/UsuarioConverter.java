package br.com.planejizze.converters;

import br.com.planejizze.dto.UsuarioDTO;
import br.com.planejizze.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UsuarioConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Usuario convertToUsuario(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public List<UsuarioDTO> convertListToDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

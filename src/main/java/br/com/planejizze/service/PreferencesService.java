package br.com.planejizze.service;

import br.com.planejizze.dto.UsuarioDTO;
import br.com.planejizze.dto.UsuarioUpdateDTO;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferencesService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PreferencesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO updateUser(UsuarioUpdateDTO usuarioUpdateDTO, Long userId) {
        var user = usuarioRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        user.get().setNome(usuarioUpdateDTO.getNome());
        user.get().setSobrenome(usuarioUpdateDTO.getSobrenome());
        var finalUser = usuarioRepository.save(user.get());
        return new UsuarioDTO(finalUser.getId(), finalUser.getUsername(), finalUser.getNome(), finalUser.getSobrenome());
    }
}

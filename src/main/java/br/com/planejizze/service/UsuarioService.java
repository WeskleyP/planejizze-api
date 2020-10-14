package br.com.planejizze.service;

import br.com.planejizze.dto.UserCountDTO;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService extends AbstractService<Usuario, Long, UsuarioRepository> {
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }

    public List<UserCountDTO> findUsersCountByRole() throws JsonProcessingException {
        var userList = new ArrayList<UserCountDTO>();
        for (var li : repo.findUsersCountWithRole()) {
            userList.add(new ObjectMapper().readValue(li, UserCountDTO.class));
        }
        return userList;
    }
}

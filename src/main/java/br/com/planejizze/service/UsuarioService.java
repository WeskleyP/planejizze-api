package br.com.planejizze.service;

import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends AbstractService<Usuario, Long, UsuarioRepository> {
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }
}

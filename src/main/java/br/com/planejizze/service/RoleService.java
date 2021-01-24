package br.com.planejizze.service;

import br.com.planejizze.model.Role;
import br.com.planejizze.repository.RoleRepository;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UsuarioRepository usuarioRepository) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void changeRolesByUser(Long id, List<Long> rolesId) {
        var roles = roleRepository.findAllById(rolesId);
        var usuario = usuarioRepository.findById(id).get();
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
    }
}

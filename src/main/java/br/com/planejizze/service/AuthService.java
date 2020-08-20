package br.com.planejizze.service;

import br.com.planejizze.dto.auth.RegisterDTO;
import br.com.planejizze.exceptions.auth.DifferentPasswordException;
import br.com.planejizze.exceptions.auth.EmailExistsException;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Object registerNewUser (RegisterDTO registerDTO){
        if (usuarioRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new EmailExistsException("Já existe uma conta cadastrada com o email:" + registerDTO.getEmail());
        }
        Usuario user = new Usuario();
        user.setNome(registerDTO.getNome());
        user.setSobrenome(registerDTO.getSobrenome());
        user.setSenha(passwordEncoder.encode(registerDTO.getSenha()));
        user.setContraSenha(passwordEncoder.encode(registerDTO.getSenhaConfirmação()));
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setIsActive(true);
        user.setEmailVerified(false);
        if(!user.getSenha().equals(user.getContraSenha())){
            throw new DifferentPasswordException("As senhas informadas estão divergentes");
        }
        // TODO implementar roles default
//        user.setRole(new Role(Integer.valueOf(1), user));
        return usuarioRepository.save(user);
    }
}

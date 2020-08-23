package br.com.planejizze.service;

import br.com.planejizze.dto.auth.RegisterDTO;
import br.com.planejizze.exceptions.auth.DifferentPasswordException;
import br.com.planejizze.exceptions.auth.EmailExistsException;
import br.com.planejizze.model.Comprovante;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.ComprovanteRepository;
import br.com.planejizze.repository.RoleRepository;
import br.com.planejizze.repository.UsuarioRepository;
import br.com.planejizze.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final ComprovanteRepository comprovanteRepository;
    @Value("${default.dns}")
    private String applicationDns;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, RoleRepository roleRepository, EmailService emailService, ComprovanteRepository comprovanteRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.comprovanteRepository = comprovanteRepository;
    }

    public Usuario registerNewUser(RegisterDTO registerDTO) {
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
        if (!user.getSenha().equals(user.getContraSenha())) {
            throw new DifferentPasswordException("As senhas informadas estão divergentes");
        }
        user.setRoles(Collections.singletonList(roleRepository.findById(1L).get()));
        Usuario usuario = new Usuario();
        try {
            usuario = usuarioRepository.save(user);
            this.createAndSendAccountConfirmationEmail(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private void createAndSendAccountConfirmationEmail(Usuario usuario) {
        String token = UUIDUtils.generateToken();
        String body = "Para confirmar sua conta, clique no seguinte link: " +
                "<a href='" + this.applicationDns +
                "/auth/confirm_account?token=" + token +
                "' a> Confirmar sua conta</a>";
        criarComprovante(usuario, token);
        emailService.sendMessage(usuario.getEmail(), "Confirme sua conta", body);
    }

    public void criarComprovante(Usuario usuario, String token) {
        long date = Instant.now().toEpochMilli() / 1000L;
        Comprovante comprovante =
                Comprovante.builder()
                        .active(true)
                        .requestedAt(date)
                        .user(usuario)
                        .uuid(token)
                        .expire(date + 86400)
                        .payload("{\"type\": \"account_confirmation\"}")
                        .build();
        comprovanteRepository.save(comprovante);
    }
}

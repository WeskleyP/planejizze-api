package br.com.planejizze.service;

import br.com.planejizze.dto.ForgetPasswordDTO;
import br.com.planejizze.dto.auth.RegisterDTO;
import br.com.planejizze.exceptions.ComprovanteException;
import br.com.planejizze.exceptions.NotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

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

    @Transactional(rollbackFor = Exception.class)
    public Usuario registerNewUser(RegisterDTO registerDTO) {
        if (usuarioRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new EmailExistsException("Já existe uma conta cadastrada com o email:" + registerDTO.getEmail());
        }
        if (!registerDTO.getSenha().equals(registerDTO.getSenhaConfirmação())) {
            throw new DifferentPasswordException("As senhas informadas estão divergentes!");
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

    private void createAndSendAccountConfirmationEmail(Usuario usuario) throws MessagingException {
        String token = UUIDUtils.generateToken();
        String body = "Para confirmar sua conta, clique no seguinte link: " +
                "<a href='" + this.applicationDns +
                "/auth/confirmAccount?token=" + token +
                "' a> Confirmar sua conta</a>";
        String type = "{\"type\": \"account_confirmation\"}";
        criarComprovante(usuario, token, type);
        emailService.sendMessage(usuario.getEmail(), "Confirme sua conta", body);
    }

    public void criarComprovante(Usuario usuario, String token, String payload) {
        long date = Instant.now().toEpochMilli() / 1000L;
        Comprovante comprovante =
                Comprovante.builder()
                        .setActive(true)
                        .setRequestedAt(date)
                        .setUser(usuario)
                        .setUuid(token)
                        .setExpire(date + 86400)
                        .setPayload(payload)
                        .build();
        comprovanteRepository.save(comprovante);
    }

    public void confirmUserAccount(String token) {
        if (!Optional.ofNullable(token).isPresent()) {
            throw new ComprovanteException("Token não informado!");
        }
        Optional<Usuario> usuario = usuarioRepository.findUsuarioUsingConfirmationAccountToken(token);
        if (!usuario.isPresent()) {
            throw new ComprovanteException("Usuário não encontrado ou comprovante expirado!");
        }
        Usuario user = usuario.get();
        user.setEmailVerified(true);
        usuarioRepository.save(user);
    }

    public void resendEmailConfirmation(String email) throws MessagingException {
        Optional<Usuario> usuario = usuarioRepository.findOneByEmail(email);
        if (usuario.isPresent()) {
            Usuario user = usuario.get();
            if (user.getEmail() == null) {
                throw new ComprovanteException("O email não está cadastrado!");
            }
            if (user.getEmailVerified()) {
                throw new ComprovanteException("O email já está verificado!");
            }
            createAndSendAccountConfirmationEmail(user);
        } else {
            throw new NotFoundException("Usuário não encontrado!");
        }
    }

    public void generateAndSendNewPassword(String email) throws MessagingException {
        Optional<Usuario> usuario = usuarioRepository.findOneByEmail(email);
        if (usuario.isPresent()) {
            Usuario user = usuario.get();
            if (user.getEmail() == null) {
                throw new ComprovanteException("O email não está cadastrado!");
            }
            if (!user.getEmailVerified()) {
                throw new ComprovanteException("O usuário não está verificado!");
            }
            sendForgetPasswordEmail(user);
        } else {
            throw new NotFoundException("Usuário não encontrado!");
        }
    }

    private void sendForgetPasswordEmail(Usuario user) throws MessagingException {
        String token = UUIDUtils.generateToken();
        String payload = "{\"type\": \"forget_password\"}";
        criarComprovante(user, token, payload);
        String subject = "Recuperar sua senha";
        String body = "Clique " +
                "<a href='" +
                applicationDns + "/auth/resetPassword?token=" +
                token + "'" +
                ">aqui</a>" +
                " para recuperar sua senha";
        emailService.sendMessage(user.getEmail(), subject, body);
    }

    public Usuario verifyForgetPasswordVoucher(String token) {
        if (!Optional.ofNullable(token).isPresent()) {
            throw new ComprovanteException("Comprovante não informado!");
        }
        Optional<Usuario> usuario = usuarioRepository.findOneByForgetPasswordVoucher(token);
        if (!usuario.isPresent()) {
            throw new ComprovanteException("Usuário não encontrado ou comprovante expirado!");
        }
        return usuario.get();
    }

    public void changePassword(String token, ForgetPasswordDTO forgetPasswordDTO) {
        Usuario user = verifyForgetPasswordVoucher(token);
        if (!forgetPasswordDTO.getSenha().equals(forgetPasswordDTO.getContraSenha())) {
            throw new DifferentPasswordException("As senhas informadas estão divergentes!");
        }
        String password = passwordEncoder.encode(forgetPasswordDTO.getSenha());
        user.setSenha(password);
        usuarioRepository.save(user);
        Comprovante comprovante = comprovanteRepository.findById(token).get();
        comprovante.setActive(false);
        comprovanteRepository.save(comprovante);
    }
}

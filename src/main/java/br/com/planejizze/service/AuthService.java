package br.com.planejizze.service;

import br.com.planejizze.config.jwt.JwtTokenProvider;
import br.com.planejizze.dto.ForgetPasswordDTO;
import br.com.planejizze.dto.auth.LoginResponseDTO;
import br.com.planejizze.dto.auth.RegisterDTO;
import br.com.planejizze.exceptions.ComprovanteException;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.exceptions.auth.DifferentPasswordException;
import br.com.planejizze.exceptions.auth.EmailExistsException;
import br.com.planejizze.exceptions.auth.InvalidJwtAuthenticationException;
import br.com.planejizze.model.Comprovante;
import br.com.planejizze.model.Usuario;
import br.com.planejizze.repository.ComprovanteRepository;
import br.com.planejizze.repository.RoleRepository;
import br.com.planejizze.repository.UsuarioRepository;
import br.com.planejizze.utils.UUIDUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ComprovanteRepository comprovanteRepository;
    @Value("${default.dns}")
    private String applicationDns;
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, RoleRepository roleRepository, EmailService emailService, JwtTokenProvider jwtTokenProvider, ComprovanteRepository comprovanteRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.jwtTokenProvider = jwtTokenProvider;
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
        var user = new Usuario();
        user.setNome(registerDTO.getNome());
        user.setSobrenome(registerDTO.getSobrenome());
        user.setSenha(passwordEncoder.encode(registerDTO.getSenha()));
        user.setContraSenha(passwordEncoder.encode(registerDTO.getSenhaConfirmação()));
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setAtivo(true);
        user.setEmailVerified(false);
        user.setRoles(Collections.singletonList(roleRepository.findById(1L).get()));
        var usuario = new Usuario();
        try {
            usuario = usuarioRepository.save(user);
            this.createAndSendAccountConfirmationEmail(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private void createAndSendAccountConfirmationEmail(Usuario usuario) throws MessagingException {
        var token = UUIDUtils.generateToken();
        var body = "Para confirmar sua conta, clique no seguinte link: " +
                "<a href='" + this.applicationDns +
                "/auth/confirmAccount?token=" + token +
                "' a> Confirmar sua conta</a>";
        var type = "{\"type\": \"account_confirmation\"}";
        criarComprovante(usuario, token, type);
        emailService.sendMessage(usuario.getEmail(), "Confirme sua conta", body);
    }

    protected void criarComprovante(Usuario usuario, String token, String payload) {
        var date = Instant.now().toEpochMilli() / 1000L;
        var comprovante =
                Comprovante.builder()
                        .setAtivo(true)
                        .setRequestedAt(date)
                        .setUser(usuario)
                        .setUuid(token)
                        .setExpire(date + 86400)
                        .setPayload(payload)
                        .build();
        comprovanteRepository.save(comprovante);
    }

    public void confirmUserAccount(String token) {
        if (token.isEmpty()) {
            throw new ComprovanteException("Token não informado!");
        }
        var usuario = usuarioRepository.findUsuarioUsingConfirmationAccountToken(token);
        if (usuario.isEmpty()) {
            throw new ComprovanteException("Usuário não encontrado ou comprovante expirado!");
        }
        var user = usuario.get();
        user.setEmailVerified(true);
        usuarioRepository.save(user);
    }

    public void resendEmailConfirmation(String email) throws MessagingException {
        var usuario = usuarioRepository.findOneByEmail(email);
        if (usuario.isPresent()) {
            var user = usuario.get();
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
        var usuario = usuarioRepository.findOneByEmail(email);
        if (usuario.isPresent()) {
            var user = usuario.get();
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

    protected void sendForgetPasswordEmail(Usuario user) throws MessagingException {
        var token = UUIDUtils.generateToken();
        var payload = "{\"type\": \"forget_password\"}";
        criarComprovante(user, token, payload);
        var subject = "Recuperar sua senha";
        var body = "Clique " +
                "<a href='" +
                applicationDns + "/auth/resetPassword?token=" +
                token + "'" +
                ">aqui</a>" +
                " para recuperar sua senha";
        emailService.sendMessage(user.getEmail(), subject, body);
    }

    public Usuario verifyForgetPasswordVoucher(String token) {
        if (token.isEmpty()) {
            throw new ComprovanteException("Comprovante não informado!");
        }
        Optional<Usuario> usuario = usuarioRepository.findOneByForgetPasswordVoucher(token);
        if (usuario.isEmpty()) {
            throw new ComprovanteException("Usuário não encontrado ou comprovante expirado!");
        }
        return usuario.get();
    }

    public void changePassword(String token, ForgetPasswordDTO forgetPasswordDTO) {
        var user = verifyForgetPasswordVoucher(token);
        if (!forgetPasswordDTO.getSenha().equals(forgetPasswordDTO.getContraSenha())) {
            throw new DifferentPasswordException("As senhas informadas estão divergentes!");
        }
        var password = passwordEncoder.encode(forgetPasswordDTO.getSenha());
        user.setSenha(password);
        usuarioRepository.save(user);
        var comprovante = comprovanteRepository.findById(token).get();
        comprovante.setAtivo(false);
        comprovanteRepository.save(comprovante);
    }

    public Jws<Claims> readToken(String token) {
        return Jwts.parser().setSigningKey(this.secretKey.getBytes()).parseClaimsJws(token);
    }

    public LoginResponseDTO refreshToken(String refresh) {
        isValid(refresh);
        var claims = readToken(refresh).getBody();
        var email = claims.getSubject();
        var usuario = this.usuarioRepository.findByEmail(email);
        if (usuario.isEmpty()) {
            throw new NotFoundException("Usuário inativo ou não encontrado");
        }
        var permissions = usuario.get().getRoles();
        var userId = claims.get("user", Long.class);
        var token = jwtTokenProvider.createToken(claims.getSubject(), permissions, userId);
        var refreshToken = jwtTokenProvider.createRefreshToken(claims.getSubject(), permissions, userId);
        return new LoginResponseDTO(token, refreshToken);
    }

    private void isValid(String refresh) {
        Jws<Claims> claims;
        try {
            claims = readToken(refresh);
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Não foi possível interpretar o token informado!");
        }
        if (SignatureAlgorithm.valueOf(claims.getHeader().getAlgorithm()) != SignatureAlgorithm.HS256)
            throw new InvalidJwtAuthenticationException(String.format("O algoritmo %s não era esperado", claims.getHeader().getAlgorithm()));
        try {
            claims.getBody().get("user", Long.class);
        } catch (Exception ignore) {
            throw new InvalidJwtAuthenticationException("Usuário não informado");
        }
        try {
            claims.getBody().get("permissions", List.class);
        } catch (Exception ignore) {
            throw new InvalidJwtAuthenticationException("Lista de permissões não informada");
        }
        if (Instant.now().isAfter(claims.getBody().getExpiration().toInstant())) {
            throw new InvalidJwtAuthenticationException("Token está expirado");
        }
    }
}

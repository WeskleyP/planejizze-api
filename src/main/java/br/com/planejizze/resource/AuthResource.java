package br.com.planejizze.resource;

import br.com.planejizze.config.jwt.JwtTokenProvider;
import br.com.planejizze.converters.UsuarioConverter;
import br.com.planejizze.dto.ForgetPasswordDTO;
import br.com.planejizze.dto.UsuarioDTO;
import br.com.planejizze.dto.auth.*;
import br.com.planejizze.exceptions.EmailNotFoundException;
import br.com.planejizze.exceptions.auth.BadCredentialsException;
import br.com.planejizze.exceptions.auth.EmailNotVerifiedException;
import br.com.planejizze.repository.UsuarioRepository;
import br.com.planejizze.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Api(tags = "Auth")
@RestController
@RequestMapping(path = "/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;
    private final UsuarioConverter usuarioConverter;
    @Value("${default.front-url}")
    private String frontUrl;

    @Autowired
    public AuthResource(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioRepository usuarioRepository, AuthService authService, UsuarioConverter usuarioConverter) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.authService = authService;
        this.usuarioConverter = usuarioConverter;
    }

    @ApiOperation("Login na aplicação")
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));
            var usuario = this.usuarioRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new EmailNotFoundException("Email " + loginDTO.getEmail() + "not found"));
            if (!usuario.getEmailVerified()) {
                throw new EmailNotVerifiedException("Email não verificado!");
            }
            var token = jwtTokenProvider.createToken(loginDTO.getEmail(), usuario.getRoles(), usuario.getId());
            var refreshToken = jwtTokenProvider.createRefreshToken(loginDTO.getEmail(), usuario.getRoles(), usuario.getId());
            return ResponseEntity.ok(new LoginResponseDTO(token, refreshToken));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Email ou senha inválidos!");
        }
    }

    @PostMapping(path = "/refreshToken")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDTO.getRefreshToken()));
    }

    @PostMapping(path = "/readToken")
    public ResponseEntity<Jws<Claims>> readToken(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(authService.readToken(tokenDTO.getToken()));
    }

    @ApiOperation("Registrar-se na aplicação")
    @PostMapping(path = "/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(usuarioConverter.convertToDTO(authService.registerNewUser(registerDTO)));
    }

    @ApiOperation("Confirmar a conta")
    @GetMapping(path = "/confirmAccount")
    public ResponseEntity confirmAccount(@RequestParam("token") String token) {
        try {
            authService.confirmUserAccount(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, frontUrl + "/email-situation?situation=error").build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, frontUrl + "/email-situation?situation=success").build();
    }

    @ApiOperation("Reenviar email de confirmação de conta")
    @PostMapping(path = "/resendEmailConfirmation")
    public ResponseEntity resendEmailConfirmation(@RequestBody String email) throws MessagingException {
        authService.resendEmailConfirmation(email);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Gerar email com token para recuperação de senha")
    @PostMapping(path = "/forgetPassword")
    public ResponseEntity forgetPassword(@RequestBody String email) throws MessagingException {
        authService.generateAndSendNewPassword(email);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Verifica se o token de recuperação de senha está válido e redireciona para a página de troca de senha")
    @GetMapping(path = "/resetPassword")
    public ResponseEntity resetPassword(@RequestParam("token") String token) {
        try {
            authService.verifyForgetPasswordVoucher(token);
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, frontUrl + "/redefine-password?token=" + token).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, frontUrl + "/redefine-password?error").build();
        }
    }

    @ApiOperation("Altera a senha que o usuário tinha esquecido")
    @PostMapping(path = "/passwordForget")
    public ResponseEntity changePassword(@RequestParam("token") String token, @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        authService.changePassword(token, forgetPasswordDTO);
        return ResponseEntity.ok().build();
    }
}

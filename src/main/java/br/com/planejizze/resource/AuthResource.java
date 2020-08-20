package br.com.planejizze.resource;

import br.com.planejizze.config.jwt.JwtTokenProvider;
import br.com.planejizze.dto.auth.LoginDTO;
import br.com.planejizze.dto.auth.LoginResponseDTO;
import br.com.planejizze.exceptions.EmailNotFoundException;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthResource(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));
            String token = jwtTokenProvider.createToken(loginDTO.getEmail(),
                    this.usuarioRepository.findByEmail(loginDTO.getEmail())
                            .orElseThrow(() -> new EmailNotFoundException("Email " + loginDTO.getEmail() + "not found"))
                            .getRoles());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Email ou senha inválidos!");
        }
    }
}

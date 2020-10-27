package br.com.planejizze.resource;

import br.com.planejizze.dto.ChangePasswordDTO;
import br.com.planejizze.dto.UsuarioDTO;
import br.com.planejizze.dto.UsuarioUpdateDTO;
import br.com.planejizze.service.AuthService;
import br.com.planejizze.service.PreferencesService;
import br.com.planejizze.utils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Preferências")
@RestController
@RequestMapping(path = "/preferences")
public class PreferencesResource {

    private final PreferencesService preferencesService;
    private final AuthService authService;

    @Autowired
    public PreferencesResource(PreferencesService preferencesService, AuthService authService) {
        this.preferencesService = preferencesService;
        this.authService = authService;
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> changeUserData(@RequestBody UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request) {
        return ResponseEntity.ok(preferencesService.updateUser(usuarioUpdateDTO, TokenUtils.from(request).getUserId()));
    }

    @PutMapping
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        authService.changePassword(request, changePasswordDTO);
        return ResponseEntity.ok().build();
    }
}

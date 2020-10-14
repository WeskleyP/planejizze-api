package br.com.planejizze.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenDTO {

    private String refreshToken;
}

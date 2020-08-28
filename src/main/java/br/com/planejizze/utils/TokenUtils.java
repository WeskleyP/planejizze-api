package br.com.planejizze.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    private final HttpServletRequest request;
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Autowired
    public TokenUtils(HttpServletRequest request) {
        this.request = request;
    }

    public static TokenUtils from(HttpServletRequest request) {
        return new TokenUtils(request);
    }

    public Long getUserId() {
        String token = request.getHeader("Authorization").substring(7);
        return Long.parseLong(Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("user", String.class));
    }
}

package br.com.planejizze.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    private final HttpServletRequest request;

    public TokenUtils(HttpServletRequest request) {
        this.request = request;
    }

    public static TokenUtils from(HttpServletRequest request) {
        return new TokenUtils(request);
    }

    public Long getUserId() {
        String token = request.getHeader("Authorization").substring(7);
        return this.readWithoutSigningKey(token).get("user", Integer.class).longValue();
    }

    public Claims readWithoutSigningKey(String token) {
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        DefaultJwtParser parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(unsignedToken);
        return (Claims) jwt.getBody();
    }
}

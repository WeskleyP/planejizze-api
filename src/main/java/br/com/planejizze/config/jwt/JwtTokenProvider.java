package br.com.planejizze.config.jwt;

import br.com.planejizze.exceptions.auth.InvalidJwtAuthenticationException;
import br.com.planejizze.model.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private Long validityInMillisecondsToken;
    @Value("${security.jwt.token.refresh-expire-length}")
    private Long validityInMillisecondsRefreshToken;
    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roles, Long userId) {
        return getTokens(username, roles, userId, validityInMillisecondsToken, "common");
    }

    private String getTokens(String username, List<Role> roles, Long userId, Long validityInMillisecondsToken, String type) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("user", userId);
        claims.put("permissions", getResumedRoles(roles));
        claims.put("type", type);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillisecondsToken);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Object getResumedRoles(List<Role> roles) {
        List<Map<String, Map<String, Boolean>>> test = roles
                .stream().map(teste1 -> (Map<String, Map<String, Boolean>>) teste1.getPermissions())
                .collect(Collectors.toList());
        Optional<Map<String, Map<String, Boolean>>> last = test.stream()
                .reduce((firstMap, secondMap) ->
                        Stream.concat(firstMap.entrySet().stream(), secondMap.entrySet().stream())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                        (countInFirstMap, countInSecondMap) -> {
                                            countInFirstMap.forEach((key, value) ->
                                                    countInSecondMap.merge(key, value, (v1, v2) -> v1.equals(true) || v2));
                                            return countInSecondMap;
                                        })));
        return last.orElse(null);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

    public String createRefreshToken(String email, List<Role> roles, Long id) {
        return getTokens(email, roles, id, validityInMillisecondsRefreshToken, "refresh");
    }
}

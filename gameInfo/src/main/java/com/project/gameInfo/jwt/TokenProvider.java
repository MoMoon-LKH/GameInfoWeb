package com.project.gameInfo.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {


    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final long tokenValidityInSeconds;
    private final int refreshValidDate;


    private Key key;
    private Key refreshKey;


    public TokenProvider(
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.refresh-validity-date}") int refreshValidDate) {
        this.tokenValidityInSeconds = tokenValidityInSeconds * 1000;
        this.refreshValidDate = refreshValidDate;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        long now = (new Date()).getTime();
        Date validDate = new Date(now + this.tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject("gameInfo")
                .claim("username", authentication.getName())
                .claim("auth", authorities)
                .signWith(key ,SignatureAlgorithm.HS512)
                .setExpiration(validDate)
                .compact();

    }

    public String createRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, refreshValidDate);
        Date validDate = new Date(cal.getTimeInMillis());

        return Jwts.builder()
                .setSubject("gameinfo_refresh")
                .claim("username", authentication.getName())
                .claim("auth", authorities)
                .signWith(refreshKey, SignatureAlgorithm.HS512)
                .setExpiration(validDate)
                .compact();

    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();

        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User user = new User(claims.get("username").toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원하지않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("잘못된 JWT 토큰입니다.");
        }

        return false;
    } // 토큰 유효성 검사


}

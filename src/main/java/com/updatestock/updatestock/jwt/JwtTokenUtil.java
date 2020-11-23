package com.updatestock.updatestock.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import com.updatestock.updatestock.dto.TokenDto;
import com.updatestock.updatestock.model.Role;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    private Clock clock = DefaultClock.INSTANCE;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public TokenDto generateToken(User user)  {

        List<String> roles = new ArrayList<String>();
		for (Role role : user.getRoles()) {
			roles.add(role.getName());
		}

        // Adiciona conteúdo no token
        Claims claims = Jwts.claims();
        claims.put("name", user.getName());
        claims.put("roles", roles);

        Date createdDate = clock.now();
        String token = Jwts.builder()
                           .setClaims(claims)
                           .setSubject(user.getLogin())
                           .setIssuedAt(createdDate)
                           .signWith(SignatureAlgorithm.HS512, secret)
                           .setExpiration(new Date(System.currentTimeMillis() + 5000)) // 24h
                           .compact();
        user.setToken(token);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(this.userRepository.save(user).getToken());
        return tokenDto;
        
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}

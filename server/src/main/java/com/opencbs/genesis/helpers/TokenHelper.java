package com.opencbs.genesis.helpers;

import com.opencbs.genesis.exceptions.SessionExpiredException;
import com.opencbs.genesis.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
public class TokenHelper {
    private final static String KEY = "jaE1f3F9ywo0Tn";
    private final static SignatureAlgorithm SIGN_ALGORITHM = SignatureAlgorithm.HS256;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(SIGN_ALGORITHM, KEY)
                .compact();
    }

    public static String extractUsername(HttpServletRequest request) throws UnauthorizedException {
        try {
            String token = extractToken(request);
            final Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
            return (String) claims.get("sub");
        } catch (final SignatureException e) {
            throw new UnauthorizedException("Invalid token.");
        }catch (final ExpiredJwtException e) {
            throw new SessionExpiredException();
        }
    }

    private static String extractToken(HttpServletRequest request) throws UnauthorizedException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new UnauthorizedException("Missing or invalid Authorization header.");
        }

        return authHeader.substring(7); // The part after "Bearer "
    }
}

package com.kadirgurturk.QuizApp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtTokenProvider {

    @Value("${quest.app.key}")
    private String APP_KEY;
    @Value("${quest.expıres.tıme}")
    private long EXPIRES_TIME;

    public String generateToken(Authentication auth)
    {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();

        Date expiredDate = new Date(new Date().getTime() + EXPIRES_TIME);

        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY).compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(APP_KEY)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean ControlToken(String token){
        // ---------> Gelen Token'nın doğru olup olmadğı ve geçerli olup olmadığına bakarız

        try {
            Jwts.parser().setSigningKey(APP_KEY).parseClaimsJws(token);

            return !isTokenExpired(token);  //----> Token hala geçerli olup olmadğına bakılır
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_KEY).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}

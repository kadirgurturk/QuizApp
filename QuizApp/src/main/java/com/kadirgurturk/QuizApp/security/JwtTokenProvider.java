package com.kadirgurturk.QuizApp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtTokenProvider { //-----> We need to generate new token for every response. This class for this.

    @Value("${quest.app.key}")
    private String APP_KEY;        //-----> This is a special key for our project
    @Value("${quest.expıres.tıme}")
    private long EXPIRES_TIME;    // ------> The number that determines the expire time in seconds



    public String generateToken(Authentication auth)
    {

        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal(); //----> auth.getPrincipal function gives us to user who be to authentication.

        Date expiredDate = new Date(new Date().getTime() + EXPIRES_TIME); //------>

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId())) //----> We gave user id as a string to token
                .setIssuedAt(new Date())   //----->  Token issue date
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY).compact(); // -------> We give a algorithm and key to create new token
    }

    public Long getUserIdFromToken(String token){ // ----> This is reverse method od generateToken. We take a token and parse it;
        Claims claims = Jwts.parser()
                .setSigningKey(APP_KEY) //---> we are starting to parse jwt
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());  //------> we return ıd
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
        //We checked here whether it has expired or not.
        Date expiration = Jwts.parser().setSigningKey(APP_KEY).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}

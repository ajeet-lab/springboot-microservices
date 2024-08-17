package com.ajeet.electronic.store.security;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.security.KeyPairBuilder;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtHelper {

    // 1. Validity
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    // 2. Secret key
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    // Retrieve username from jwt token
    public String getUsernameFromToken(String token){
        return  getClaimFromToken(token, Claims::getSubject);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){

        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();
    }


    // Check if the token has expired
    public Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    // Retrieve expiration date from jwt token
   public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
   }

 // Generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }


}

package com.spring.security.jwt.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger log = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String authorization =req.getHeader("Authorization");
        log.info("Token recived from Token : "+authorization);
        String username=null;
        String token=null;
        if(authorization != null && authorization.startsWith("Bearer")){
            token = authorization.substring(7);
            try{
                username = this.helper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                log.error("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                log.error("Given jwt token is expired !!");
                e.printStackTrace();
            }catch(MalformedJwtException e){
                log.error("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            }catch (Exception e){
                log.error("In valid token!!");
                e.printStackTrace();
            }
        }else{
            log.error("Invalid token !!!");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean isValidated = this.helper.validateToken(token, userDetails);

            if(isValidated){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else{
                log.error("Invalid token received!!");
            }
        }

        filterChain.doFilter(req, res);
    }
}

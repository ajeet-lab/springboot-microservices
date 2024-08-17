package com.ajeet.electronic.store.security;

import com.ajeet.electronic.store.exceptions.BadApiRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // Steps:
    // Get token from request
    // Validate Token
    // GetUsername from token
    // Load user associated with this token
    // Set authentication to security context

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService){
        this.jwtHelper = jwtHelper;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header {} ", requestHeader);

        String username=null;

        String token=null;

        if(requestHeader != null && requestHeader.startsWith("Bearer")){
            token=requestHeader.substring(7);
           try{
               username = jwtHelper.getUsernameFromToken(token);
               logger.info("Token username : {} ", username);


           }catch (IllegalArgumentException ex){
               logger.info("Illegal Argument while fetching the username !! {}",  ex.getMessage());
           }catch (ExpiredJwtException ex){
               logger.info("Given Jwt token has expired {}", ex.getMessage());
           }catch (MalformedJwtException ex){
               logger.info("Some changed has done in token !! invalid token {}", ex.getMessage());
           }catch (Exception ex){
               logger.info("Exception ::: {}", ex.getMessage());
               ex.printStackTrace();
           }
        }else {
            logger.info("Invalid Header !! Header is not starting with Bearer");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
           UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

           if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)){
               // Add authentication in SecurityContext

               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
        }

        filterChain.doFilter(request, response);

    }
}

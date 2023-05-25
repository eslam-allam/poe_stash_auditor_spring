package com.eslam.poeauditor.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import com.eslam.poeauditor.domain.ExceptionMessageDto;
import com.eslam.poeauditor.exception.InvalidTokenException;
import com.eslam.poeauditor.service.JWTService;

import org.apache.hc.core5.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eslam.poeauditor.service.PoeUserDetailsService;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * @author Samson Effes
 */

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PoeUserDetailsService libraryUserDetailsService;

    private final Logger log = LogManager.getLogger(getClass());
    private final Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                userName = jwtService.extractUsernameFromToken(token);
            }
            if (userName != null & SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = libraryUserDetailsService.loadUserByUsername(userName);
                if(jwtService.validateToken(token, userDetails)) {
                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String jsonMessage = gson.toJson(ExceptionMessageDto.builder().errorMessage(e.getMessage())
            .requestDetails(Collections.singletonMap("url", request.getRequestURI()))
            .build());
            log.error("Failed to evaluate token. Error: {}", e.getMessage());
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            out.print(jsonMessage);
            out.flush();   

        }
        
        
    }
}

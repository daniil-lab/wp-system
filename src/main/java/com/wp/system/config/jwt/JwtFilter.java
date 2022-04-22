package com.wp.system.config.jwt;

import com.wp.system.config.security.AuthCredentials;
import com.wp.system.config.security.UserAuthDetails;
import com.wp.system.config.security.UserAuthDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

@Component
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserAuthDetailsService customUserDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtProvider.validateToken(token)) {
            Claims userData = jwtProvider.getDataFromToken(token);

            UserAuthDetails customUserDetails = null;

            if(userData.get("username") == null || ((String) userData.get("username")).length() == 0)
                customUserDetails = customUserDetailsService.loadByEmail((String) userData.get("email"));
            else
                customUserDetails = customUserDetailsService.loadUserByUsername((String) userData.get("username"));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, new AuthCredentials(customUserDetails.getId(), customUserDetails.getUsername(), customUserDetails.getPassword()), customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
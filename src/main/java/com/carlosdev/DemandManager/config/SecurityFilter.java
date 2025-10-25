package com.carlosdev.DemandManager.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final UserDetailsService userDetailsService;

    public SecurityFilter(TokenConfig tokenConfig, UserDetailsService userDetailsService) {
        this.tokenConfig = tokenConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizedHeader = request.getHeader("Authorization");
        if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {
            String token = authorizedHeader.substring("Bearer ".length());

            Optional<JwTUserData> opUser = tokenConfig.validateToken(token);


            if (opUser.isPresent()) {
                JwTUserData userDate = opUser.get();
                String email = userDate.email();

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

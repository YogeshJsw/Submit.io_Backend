package com.submitIo.filter;

import com.submitIo.service.authService.UserDetailsServiceApplyFormImpl;
import com.submitIo.service.authService.UserDetailsServiceUploadFormImpl;
import com.submitIo.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter{

    private final UserDetailsServiceUploadFormImpl userDetailsServiceUploadForm;
    private final UserDetailsServiceApplyFormImpl userDetailsServiceApplyForm;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String path = request.getRequestURI();
            UserDetails userDetails;

            if(path.startsWith("/query/form")){
                userDetails = userDetailsServiceUploadForm.loadUserByUsername(username);
                if(userDetails==null){
                    userDetails = userDetailsServiceApplyForm.loadUserByUsername(username);
                }
            }
            else if (path.startsWith("/upload") || path.startsWith("/form") || path.startsWith("/aws")) {
                userDetails = userDetailsServiceUploadForm.loadUserByUsername(username);
            } else if (path.startsWith("/apply")) {
                userDetails = userDetailsServiceApplyForm.loadUserByUsername(username);
            } else {
                chain.doFilter(request, response);
                return;
            }

            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (jwtUtil.validateToken(jwt)) {
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
        }
        chain.doFilter(request, response);
    }
}
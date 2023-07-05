package com.kadirgurturk.QuizApp.security;

import com.kadirgurturk.QuizApp.buisness.service.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class AuhFilter extends OncePerRequestFilter { //-----> Http isteklerini burada filtreleriz.

    JwtTokenProvider jwtTokenProvider;

    UserDetailServiceImp userDetailServiceImp;

    public AuhFilter() {

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ----> We added new jwt filter to default SpringSecurityfilters with this function.

        try {
            // ---> We need to extract jwt value from HttpRequest, so we write a new function to extract it
            String jwtToken = requestToJwt(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.ControlToken(jwtToken)) {
                Long id = jwtTokenProvider.getUserIdFromToken(jwtToken);
                UserDetails user = userDetailServiceImp.loadUserById(id);
                if(user != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }
        }catch(Exception e) {
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String requestToJwt(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring(7);
        return null;

    }
}

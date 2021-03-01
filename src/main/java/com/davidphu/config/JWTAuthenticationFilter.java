package com.davidphu.config;

import com.davidphu.util.JwtTokenUtil;
import com.davidphu.util.UrlTenantResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String HEADER_STRING = "User-Token";
    private static final String TOKEN_PREFIX = "";
    private static final String TENANT_ID = "tenantId";
    private JwtTokenUtil jwtTokenUtil;

    public JWTAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // add tenant claim to the token
        Map<String, Object> claims = new HashMap<>();
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver(req.getRequestURL().toString());
        claims.put(TENANT_ID, urlTenantResolver.getTenantId());

        String token = jwtTokenUtil.generateToken(claims, ((User) auth.getPrincipal()).getUsername());
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}

package com.davidphu.config;

import com.davidphu.util.JwtTokenUtil;
import com.davidphu.util.UrlTenantResolver;
import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeycloakAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final String HEADER_STRING = "User-Token";
    private static final String TOKEN_PREFIX = "";
    private static final String TENANT_ID = "tenantId";
    private JwtTokenUtil jwtTokenUtil;

    public KeycloakAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        setUserJwtToken(request, response, authentication);
        super.onAuthenticationSuccess(request, response, authentication);
        return;
    }

    protected void setUserJwtToken(HttpServletRequest req,
                                   HttpServletResponse res,
                                   Authentication auth) throws IOException {
        if (jwtTokenUtil == null) {
            jwtTokenUtil = new JwtTokenUtil();
        }

        // add tenant claim to the token
        Map<String, Object> claims = new HashMap<>();
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver(req.getRequestURL().toString());
        claims.put(TENANT_ID, urlTenantResolver.getTenantId());

        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();
        String email = principal.getKeycloakSecurityContext().getIdToken().getEmail();

        String token = jwtTokenUtil.generateToken(claims, email);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}

package com.davidphu.config;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.davidphu.util.UriTenantResolver;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class MultitenantKeycloakAuthenticationEntryPoint extends KeycloakAuthenticationEntryPoint {

    public MultitenantKeycloakAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext) {
        super(adapterDeploymentContext);
    }

    public MultitenantKeycloakAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext, RequestMatcher apiRequestMatcher) {
        super(adapterDeploymentContext, apiRequestMatcher);
    }

    @Override
    protected void commenceLoginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURL().toString();
        String tenantId = null;
        try {
            UriTenantResolver uriTenantResolver = new UriTenantResolver(path);
            tenantId = uriTenantResolver.getTenantId();
        }
        catch (URISyntaxException ex) {
            System.out.println("Invalid URI path: " + path);
            ex.printStackTrace();
        }

        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Not able to resolve tenant id from the request path!");
        }

        String contextAwareLoginUri = request.getContextPath() + "/tenant/" + tenantId + DEFAULT_LOGIN_URI;
        response.sendRedirect(contextAwareLoginUri);
    }
}

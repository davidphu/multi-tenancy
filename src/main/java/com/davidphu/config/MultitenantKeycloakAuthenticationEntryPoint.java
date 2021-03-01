package com.davidphu.config;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.davidphu.util.UrlTenantResolver;
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
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver(path);
        tenantId = urlTenantResolver.getTenantId();

        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Not able to resolve tenant id from the request path!");
        }

        String contextAwareLoginUri = request.getContextPath() + "/tenant/" + tenantId + DEFAULT_LOGIN_URI;
        response.sendRedirect(contextAwareLoginUri);
    }
}

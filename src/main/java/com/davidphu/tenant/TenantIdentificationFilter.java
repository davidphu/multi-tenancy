package com.davidphu.tenant;

import com.davidphu.util.UrlTenantResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TenantIdentificationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TenantIdentificationFilter.class);
    private static final String AuthTokenTenantId = "tenant-id";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // Get tenant id from the request url
        String requestUrl = httpServletRequest.getRequestURL().toString();
        System.out.println("requestUrl = " + requestUrl);
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver(requestUrl);
        String requestURLTenantId = urlTenantResolver.getTenantId();
        TenantContext.setTenantId(requestURLTenantId);

        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            /*Todo:
                Not sure if this check is needed or not.
                This assumes realm id is configured to match tenant id, which I think is okie
                The proper way to send the tenantid in the token is to use the mapper script
                deployed to keycloak server.  See instructions here:
                1. https://www.keycloak.org/docs/latest/server_development/#_script_providers.
            */
            Object principal = context.getAuthentication().getPrincipal();
            if (principal instanceof KeycloakPrincipal) {
                //Extract the tenantId from the authentication token
                String realmId = ((KeycloakPrincipal<KeycloakSecurityContext>) principal).getKeycloakSecurityContext().getRealm();

                //Validate token tenantId matches the request URL tenantId
                if (Strings.isNotBlank(realmId) && !realmId.equalsIgnoreCase(requestURLTenantId)) {
                    throw new ServletException("Tenant id does not match: " + requestURLTenantId + " vs " + realmId);
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
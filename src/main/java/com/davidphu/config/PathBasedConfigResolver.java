package com.davidphu.config;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

import com.davidphu.util.UrlTenantResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

public class PathBasedConfigResolver implements KeycloakConfigResolver {

    private final ConcurrentHashMap<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        String path = request.getURI();
        String tenantId = null;
        try {
            UrlTenantResolver urlTenantResolver = new UrlTenantResolver(path);
            tenantId = urlTenantResolver.getTenantId();
        }
        catch (MalformedURLException ex) {
            System.out.println("Invalid URI path: " + path);
            ex.printStackTrace();
        }

        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Not able to resolve tenant id from the request path!");
        }

        if (!cache.containsKey(tenantId)) {
            InputStream is = getClass().getResourceAsStream("/" + tenantId + "-keycloak.json");
            cache.put(tenantId, KeycloakDeploymentBuilder.build(is));
        }

        return cache.get(tenantId);
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        PathBasedConfigResolver.adapterConfig = adapterConfig;
    }

}
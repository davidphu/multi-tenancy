package com.davidphu.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UriTenantResolver {
    public static String HostDomainSeparator = "\\.";
    private URI uri;

    public UriTenantResolver(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
    }

    public UriTenantResolver(URI uri) {
        this.uri = uri;
    }

    public String getTenantId() {
        if (uri == null) {
            return null;
        }
        String[] hostComponents = uri.getHost().split(HostDomainSeparator);
        if (hostComponents.length <= 1) {
            return null;
        }
        if (hostComponents[0].startsWith("www")) {
            return hostComponents[1];
        }
        return hostComponents[0];
    }
}

package com.davidphu.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlTenantResolver {
    public static String HostDomainSeparator = "\\.";
    private URL url;

    public UrlTenantResolver(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public UrlTenantResolver(URL url) {
        this.url = url;
    }

    public String getTenantId() {
        if (url == null) {
            return null;
        }
        String[] hostComponents = url.getHost().split(HostDomainSeparator);
        if (hostComponents.length <= 1) {
            return null;
        }
        if (hostComponents[0].startsWith("www")) {
            return hostComponents[1];
        }
        return hostComponents[0];
    }
}

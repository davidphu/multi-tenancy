package com.davidphu.util;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

class UrlTenantResolverTest {
    @Test
    public void constructorHandlesValidUriString() throws MalformedURLException {
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver("http://www.abc.com");
        urlTenantResolver = new UrlTenantResolver("https://www.abc.com");
        urlTenantResolver = new UrlTenantResolver("https://tenant1.abc.com");
        urlTenantResolver = new UrlTenantResolver("https://www.tenant1.abc.com");
        urlTenantResolver = new UrlTenantResolver("https://www.abc.com/subdir1/subdir2?param1=val1&param2=val2");
    }

    @Test
    public void constructorHandlesInvalidUriString() {
        //Todo
    }

    @Test
    public void extractTenantIdFromGivenUri() throws MalformedURLException {
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver("https://www.abc.com");
        assertThat(urlTenantResolver.getTenantId()).isEqualTo("abc");
        urlTenantResolver = new UrlTenantResolver("https://www.abc.com");
        assertThat(urlTenantResolver.getTenantId()).isEqualTo("abc");
        urlTenantResolver = new UrlTenantResolver("https://tenant1.abc.com");
        assertThat(urlTenantResolver.getTenantId()).isEqualTo("tenant1");
        urlTenantResolver = new UrlTenantResolver("https://www.tenant1.abc.com");
        assertThat(urlTenantResolver.getTenantId()).isEqualTo("tenant1");
        urlTenantResolver = new UrlTenantResolver("https://localhost:8081");
        assertThat(urlTenantResolver.getTenantId()).isNull();
        urlTenantResolver = new UrlTenantResolver("https://user@www.tenant1.abc.com:8081");
        assertThat(urlTenantResolver.getTenantId()).isEqualTo("tenant1");
    }
}
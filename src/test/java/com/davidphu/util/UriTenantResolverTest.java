package com.davidphu.util;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UriTenantResolverTest {
    @Test
    public void constructorHandlesValidUriString() throws URISyntaxException {
        UriTenantResolver uriTenantResolver = new UriTenantResolver("http://www.abc.com");
        uriTenantResolver = new UriTenantResolver("https://www.abc.com");
        uriTenantResolver = new UriTenantResolver("https://tenant1.abc.com");
        uriTenantResolver = new UriTenantResolver("https://www.tenant1.abc.com");
        uriTenantResolver = new UriTenantResolver("https://www.abc.com/subdir1/subdir2?param1=val1&param2=val2");
    }

    @Test
    public void constructorHandlesInvalidUriString() throws URISyntaxException {
        //Todo
    }

    @Test
    public void extractTenantIdFromGivenUri() throws URISyntaxException {
        UriTenantResolver uriTenantResolver = new UriTenantResolver("https://www.abc.com");
        assertThat(uriTenantResolver.getTenantId()).isEqualTo("abc");
        uriTenantResolver = new UriTenantResolver("https://www.abc.com");
        assertThat(uriTenantResolver.getTenantId()).isEqualTo("abc");
        uriTenantResolver = new UriTenantResolver("https://tenant1.abc.com");
        assertThat(uriTenantResolver.getTenantId()).isEqualTo("tenant1");
        uriTenantResolver = new UriTenantResolver("https://www.tenant1.abc.com");
        assertThat(uriTenantResolver.getTenantId()).isEqualTo("tenant1");
        uriTenantResolver = new UriTenantResolver("https://localhost:8081");
        assertThat(uriTenantResolver.getTenantId()).isNull();
        uriTenantResolver = new UriTenantResolver("https://user@www.tenant1.abc.com:8081");
        assertThat(uriTenantResolver.getTenantId()).isEqualTo("tenant1");
    }
}
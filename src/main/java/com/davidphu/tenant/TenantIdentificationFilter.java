package com.davidphu.tenant;

import com.davidphu.util.UrlTenantResolver;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TenantIdentificationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TenantIdentificationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String token = httpServletRequest.getHeader(HEADER_STRING);
//        if (token != null) {
//            // parse the token.
//            String tenant = JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
//                .build()
//                .verify(token.replace(TOKEN_PREFIX, ""))
//                .getClaim("tenant").asString();
//
//            // set the tenant identifier
//            TenantContext.setTenantId(tenant);
//            logger.debug("Tenant found in the request: {}", tenant);
//        }
        String requestUrl = httpServletRequest.getRequestURL().toString();
        System.out.println("requestUrl = " + requestUrl);
        UrlTenantResolver urlTenantResolver = new UrlTenantResolver(requestUrl);
        TenantContext.setTenantId(urlTenantResolver.getTenantId());

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
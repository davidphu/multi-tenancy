package com.davidphu.user.controller;

import com.davidphu.user.UserTokenInfo;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class UserController {

    @GetMapping("/users/self")
    public ResponseEntity<UserTokenInfo> listCatalogBranch1() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        final Principal principal = (Principal) authentication.getPrincipal();

        UserTokenInfo.UserTokenInfoBuilder userTokenInfoBuilder = UserTokenInfo.builder();
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            KeycloakSecurityContext ksc = kPrincipal.getKeycloakSecurityContext();
            IDToken token = ksc.getIdToken();
            AccessToken accessToken = kPrincipal.getKeycloakSecurityContext().getToken();

            // this value is the one use to call another service as bearer token
            // Authorization : Bearer kcs.getTokenString()
            // use this link to read the token https://jwt.io
            userTokenInfoBuilder.firstName(accessToken.getGivenName())
                .lastName(accessToken.getFamilyName())
                .emailAddress(accessToken.getEmail())
                .tokenInfo(accessToken.getSubject());
        }

        System.out.println("User token info: " + userTokenInfoBuilder.build().toString());
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(userTokenInfoBuilder.build());
    }
}

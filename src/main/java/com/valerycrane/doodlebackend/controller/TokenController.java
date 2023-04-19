package com.valerycrane.doodlebackend.controller;

import org.springframework.stereotype.Controller;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TokenController {
    private final Map<String, Principal> accessTokenMap = new HashMap<>();

    public void saveAccessTokenForPrincipal(String accessToken, Principal principal) {
        accessTokenMap.put(accessToken, principal);
    }

    public Principal getPrincipalForAccessToken(String accessToken) {
        return accessTokenMap.get(accessToken);
    }
}

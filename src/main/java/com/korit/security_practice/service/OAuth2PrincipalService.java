package com.korit.security_practice.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuth2PrincipalService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = (String) attributes.get("email");
        String providerUserId = attributes.get("sub").toString();

        Map<String, Object> newAttributes = Map.of(
                "provider", provider,
                "providerUserId", providerUserId,
                "email", email
        );

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));

        return new DefaultOAuth2User(authorities, newAttributes, "providerUserId");
    }
}












package br.com.iftoauth.security;

import br.com.iftoauth.jpa.AuthUser;
import java.util.HashMap;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    if (authentication.getPrincipal() instanceof AuthUser) {
      var auth = (AuthUser) authentication.getPrincipal();
      var info = new HashMap<String, Object>();
      info.put("id", auth.getId());
      info.put("full_name", auth.getFullName());

      var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
      oAuth2AccessToken.setAdditionalInformation(info);

    }
    return accessToken;
  }
}

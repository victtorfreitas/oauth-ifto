package br.com.iftoauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  private final TokenProperty tokenProperty;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("app-ifto")
        .secret(passwordEncoder.encode("web123"))
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("write", "read")
        .accessTokenValiditySeconds(tokenProperty.getValiditySeconds())
        .refreshTokenValiditySeconds(tokenProperty.getValiditySeconds() * 2)
        .and()
        .withClient("checktoken")
        .secret(passwordEncoder.encode("check123"));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .reuseRefreshTokens(false)
        .accessTokenConverter(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey(tokenProperty.getSecretKey());
    return jwtAccessTokenConverter;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.checkTokenAccess("isAuthenticated()");
  }
}

package br.com.iftoauth.security;

import br.com.iftoauth.property.ApiProperty;
import br.com.iftoauth.property.TokenProperty;
import java.security.KeyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  public static final String STORE_KEY_OAUTH_IFTO_JKS = "store/key/oauth_ifto.jks";
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final TokenProperty tokenProperty;
  private final ApiProperty apiProperty;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient(apiProperty.getClientId())
        .secret(passwordEncoder.encode(apiProperty.getSecretId()))
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("write", "read")
        .accessTokenValiditySeconds(tokenProperty.getValiditySeconds())
        .refreshTokenValiditySeconds(tokenProperty.getValiditySeconds() * 2)
        .and()
        .withClient("checktoken")
        .secret(passwordEncoder.encode("check123"));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .reuseRefreshTokens(false)
        .accessTokenConverter(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    final var jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setKeyPair(getKeyPair());
    return jwtAccessTokenConverter;
  }

  private KeyPair getKeyPair() {
    return getKeyStoreKeyFactory()
        .getKeyPair(tokenProperty.getPairAliasKey());
  }

  private KeyStoreKeyFactory getKeyStoreKeyFactory() {
    final var pathResource = new ClassPathResource(STORE_KEY_OAUTH_IFTO_JKS);

    return new KeyStoreKeyFactory(pathResource, tokenProperty.getStorePassKeyCharArray());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.checkTokenAccess("isAuthenticated()");
  }
}

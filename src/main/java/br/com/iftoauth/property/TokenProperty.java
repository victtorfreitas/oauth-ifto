package br.com.iftoauth.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class TokenProperty {

  @Value("${token.secret.store-pass}")
  private String storePassKey;

  @Value("${token.secret.pair-alias}")
  private String pairAliasKey;

  @Value("${token.validity.seconds}")
  private int validitySeconds;

  public char[] getStorePassKeyCharArray() {
    return storePassKey.toCharArray();
  }
}

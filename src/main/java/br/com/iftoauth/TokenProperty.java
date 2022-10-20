package br.com.iftoauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class TokenProperty {

  @Value("${token.secret.key}")
  private String secretKey;
  @Value("${token.validity.seconds}")
  private int validitySeconds;
}

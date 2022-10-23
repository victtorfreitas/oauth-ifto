package br.com.iftoauth.security.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class ApiProperty {

  @Value("${api.client-id}")
  private String clientId;

  @Value("${api.secret-id}")
  private String secretId;
}

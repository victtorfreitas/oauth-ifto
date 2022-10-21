package br.com.iftoauth.jpa;

import br.com.iftoauth.jpa.entity.UserEntity;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class AuthUser extends User {

  private final String email;

  public AuthUser(UserEntity userEntity) {
    super(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    email = userEntity.getEmail();
  }
}

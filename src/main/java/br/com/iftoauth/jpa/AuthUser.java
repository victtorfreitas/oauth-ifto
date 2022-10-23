package br.com.iftoauth.jpa;

import br.com.iftoauth.jpa.entity.UserEntity;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class AuthUser extends User {

  private final String email;
  private final String fullName;
  private final String id;

  public AuthUser(UserEntity userEntity) {
    super(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    email = userEntity.getEmail();
    fullName = userEntity.getName();
    id = userEntity.getUuid();
  }
}

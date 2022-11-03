package br.com.iftoauth.jpa;

import br.com.iftoauth.jpa.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class AuthUser extends User {

  private final String email;
  private final String fullName;
  private final String id;

  public AuthUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
    super(userEntity.getEmail(), userEntity.getPassword(), authorities);
    email = userEntity.getEmail();
    fullName = userEntity.getName();
    id = userEntity.getUuid();
  }
}

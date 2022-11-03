package br.com.iftoauth.jpa;

import br.com.iftoauth.jpa.entity.UserEntity;
import br.com.iftoauth.jpa.repository.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final var userEntity = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(getMsg(username)));
    return new AuthUser(userEntity, getAuthorities(userEntity));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(UserEntity userEntity) {
    return userEntity.getGroups()
        .stream()
        .map(groupUserEntity -> new SimpleGrantedAuthority(groupUserEntity.getName().toUpperCase()))
        .collect(Collectors.toSet());
  }

  private static String getMsg(String username) {
    return String.format("User %s not found", username);
  }
}

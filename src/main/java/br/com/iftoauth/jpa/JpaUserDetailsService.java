package br.com.iftoauth.jpa;

import br.com.iftoauth.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    final var userEntity = userRepository.findByEmail(username).orElseThrow(
        () -> new UsernameNotFoundException(String.format("User %s not found", username)));
    return new AuthUser(userEntity);
  }
}

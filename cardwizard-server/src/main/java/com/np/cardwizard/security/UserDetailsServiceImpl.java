package com.np.cardwizard.security;

import com.np.cardwizard.model.user.AbstractUser;
import com.np.cardwizard.model.user.AdminUser;
import com.np.cardwizard.model.user.User;
import com.np.cardwizard.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AbstractUser abstractUser = userRepository.findByUserName(username);

    if (abstractUser == null) {
      LOGGER.debug("No User found for username: '" + username + "'");
      throw new UsernameNotFoundException(
          messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
    }

    if (abstractUser instanceof AdminUser) {
      return new com.np.cardwizard.security.UserDetails(abstractUser.getUserName(), abstractUser.getPassword(),
          true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.toRoleString())), abstractUser.getId(), Role.ADMIN);
    } else if (abstractUser instanceof User) {
      return new com.np.cardwizard.security.UserDetails(abstractUser.getUserName(), abstractUser.getPassword(),
          true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority(Role.USER.toRoleString())), abstractUser.getId(), Role.USER);
    } else {
      throw new IllegalStateException();
    }
  }
}

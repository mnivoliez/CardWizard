package com.np.cardwizard.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class UserDetails extends User {
  private static final long serialVersionUID = 1L;

  private final int id;
  private final Role role;

  public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, int id, Role role) {
    super(username, password, authorities);
    this.id = id;
    this.role = role;
  }

  public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, int id, Role role) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.id = id;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public Role getRole() {
    return role;
  }
}

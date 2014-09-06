package com.np.cardwizard.security;

public enum Role {
  USER, ADMIN;

  public String toRoleString() {
    return "ROLE_" + this.toString();
  }
}

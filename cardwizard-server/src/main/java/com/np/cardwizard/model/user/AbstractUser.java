package com.np.cardwizard.model.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractUser implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;

  @Basic
  @Column(name = "userName", nullable = false, unique = true)
  private String userName;

  @Basic
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Basic
  @Column(name = "password", nullable = false)
  private String password;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

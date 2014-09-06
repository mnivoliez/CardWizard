package com.np.cardwizard.repository;

import com.np.cardwizard.model.user.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AbstractUser, Integer> {

  public AbstractUser findByUserName(String userName);
}

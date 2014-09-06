package com.np.cardwizard.repository;

import com.np.cardwizard.CardWizardConfig;
import com.np.cardwizard.model.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CardWizardConfig.class)
@WebAppConfiguration
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testCreateUser() throws Exception {
    User user = new User();
    user.setUserName("Sponge Bob");
    user.setEmail("sponge.bob@test.fr");
    user.setPassword("test");
    user = userRepository.saveAndFlush(user);
    Assert.assertNotNull(user);
  }
}

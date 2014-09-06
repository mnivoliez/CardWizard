package com.np.cardwizard;

import com.np.cardwizard.config.CardWizardProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

import static java.lang.String.format;

public class CardWizardApplication extends SpringApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(CardWizardApplication.class);

  public CardWizardApplication(Object... sources) {
    super(sources);
  }

  @Override
  protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
    super.configureProfiles(environment, args);

    boolean developmentActive = environment.acceptsProfiles(CardWizardProfile.DEVELOPMENT);
    boolean productionActive = environment.acceptsProfiles(CardWizardProfile.PRODUCTION);

    if (developmentActive && productionActive) {
      throw new IllegalStateException(format("Only one of the following profiles may be specified: %s",
          Arrays.toString(new String[]{CardWizardProfile.DEVELOPMENT, CardWizardProfile.PRODUCTION})));
    }

    if (productionActive) {
      LOGGER.info("The '{}' profile is active.", CardWizardProfile.PRODUCTION);
    } else if (developmentActive) {
      LOGGER.info("The default '{}' profile is active because no other profiles have been specified.", CardWizardProfile.DEVELOPMENT);
    } else {
      throw new IllegalStateException(format("Unknown profile(s) specified: [%s]. Valid profiles are: [%s]",
          Arrays.toString(environment.getActiveProfiles()),
          Arrays.toString(new String[]{CardWizardProfile.DEVELOPMENT, CardWizardProfile.PRODUCTION})));
    }
  }

  public static void main(String[] args) {
    new CardWizardApplication(CardWizardConfig.class).run(args);
    LOGGER.info("L'application a bien démarre.");

  }
}

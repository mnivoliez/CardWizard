package com.np.cardwizard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Created by eraindil on 01/08/14.
 */
public class CardWizardApplication extends SpringApplication{

    private static final Logger LOGGER = LoggerFactory.getLogger(CardWizardApplication.class);

    public CardWizardApplication(Object... sources){
        super(sources);
    }

    public static void main(String[] args){
        new CardWizardApplication(CardWizardConfig.class).run(args);
        LOGGER.info("L'application a bien démarre.");
    }
}

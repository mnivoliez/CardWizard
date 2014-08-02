package com.np.cardwizard.config;

/**
 * Created by eraindil on 02/08/14.
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ClientResourceConfig extends WebMvcConfigurerAdapter {

    @Value("${CARDWIZARD_HOME:}")
    private String cardwizardPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.cardwizardPath.isEmpty()) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("file://" + this.cardwizardPath + "/cardwizard-client/src/")
                    .setCachePeriod(0);
        }
    }
}

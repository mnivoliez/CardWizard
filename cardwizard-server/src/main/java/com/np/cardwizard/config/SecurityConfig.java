package com.np.cardwizard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private Environment environment;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/favicon.ico", "/index.html", "app.min.js", "app.min.css", "/app/**", "/img/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .exceptionHandling()
        .authenticationEntryPoint(ajaxAuthenticationEntryPoint())
        .and()
      .formLogin()
        .successHandler(ajaxAuthenticationSuccessHandler())
        .failureHandler(ajaxAuthenticationFailureHandler())
        .loginProcessingUrl("/process-login")
        .permitAll()
        .and()
      .rememberMe()
        .key("8E90BE60-0CFC-11E4-9191-0800200C9A66")
        .tokenValiditySeconds(7776000) // 90 days
        .and()
      .logout()
        .logoutUrl("/process-logout")
        .logoutSuccessHandler(ajaxLogoutSuccessHandler())
        .permitAll()
        .and()
      .csrf()
        .disable()
      .authorizeRequests()
        .antMatchers("**").permitAll()
        .anyRequest().permitAll(); // TODO use explicit authorization instead
    // @formatter:on
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // @formatter:off
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
    // @formatter:on
  }

  @Bean
  public AuthenticationEntryPoint ajaxAuthenticationEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }

  @Bean
  public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      private RequestCache requestCache = new HttpSessionRequestCache();

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws ServletException, IOException {

        if (requestCache.getRequest(request, response) != null) {
          requestCache.removeRequest(request, response);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();

        clearAuthenticationAttributes(request);
      }

      @SuppressWarnings("UnusedDeclaration")
      public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
      }
    };
  }

  @Bean
  public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
    return (request, response, exception) ->
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
  }

  @Bean
  public LogoutSuccessHandler ajaxLogoutSuccessHandler() {
    return (request, response, authentication) -> {
      response.setStatus(HttpServletResponse.SC_OK);
      response.flushBuffer();
    };
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  /*@Configuration
  @Order(1)
  public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
      // @formatter:off

      // @formatter:on
    }
  }*/
}

package no.ntnu.websitebackendspringboot.security;

import no.ntnu.websitebackendspringboot.filter.CustomAuthenticationFilter;
import no.ntnu.websitebackendspringboot.filter.CustomAuthorizationFilter;
import no.ntnu.websitebackendspringboot.services.JwtService;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Configuration
@EnableWebSecurity //tells that this is a class for configuring web security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final JwtService jwtService;
  private final UserService userService;


  public SecurityConfig(
          UserDetailsService userDetailsService, JwtService jwtService, UserService userService) {
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  /**
   * This method will be called automatically by the framework to find out what authentication to use.
   * Here we tell that we want to load users from a database
   *
   * @param auth Authentication builder
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //Want to have the CustomAuthenticationFilter to override the default login url path
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
            authenticationManagerBean(), jwtService);
//    customAuthenticationFilter.setFilterProcessesUrl("/login");

    //Disable cross site request forgery to allow JWT authentication
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    http.authorizeRequests()
            .antMatchers("/", "/home", "/login", "/store", "/about", "/faq" , "/gallery").permitAll()
                    .antMatchers("/css/**", "/images/**", "/js/**", "/api/footer", "/api/header").permitAll();

    //, "/css/**", "/images/**", "/js/**", "/api/**"

    //We want everyone authenticated
    http.authorizeRequests().anyRequest().authenticated();

    //Adding a filter.
    http.addFilter(customAuthenticationFilter);

    //Want to authorize (Authorization) before we authenticate (Authentication)
    http.addFilterBefore(new CustomAuthorizationFilter(jwtService, userService), UsernamePasswordAuthenticationFilter.class);


  }


  /**
   * This is needed since Spring Boot 2.0, see
   * https://stackoverflow.com/questions/52243774/consider-defining-a-bean-of-type-org-springframework-security-authentication-au
   *
   * @return
   * @throws Exception
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }



}

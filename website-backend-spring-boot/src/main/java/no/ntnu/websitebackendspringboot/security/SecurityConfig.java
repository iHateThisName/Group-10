package no.ntnu.websitebackendspringboot.security;

import no.ntnu.websitebackendspringboot.filter.CustomAuthenticationFilter;
import no.ntnu.websitebackendspringboot.filter.CustomAuthorizationFilter;
import no.ntnu.websitebackendspringboot.services.JwtService;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
 *
 * This class is where we will configure the http security of the website.
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
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  /**
   * Here we are able to configure the http security of the website.
   *
   * @param http an object the represents the security of the website.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //Disable cross site request forgery since we are using JWT authentication
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    //Certain path should be public
    http.authorizeRequests()
            .antMatchers("/", "/home", "/login", "/store", "/about", "/faq" , "/gallery", "/register").permitAll()
            .antMatchers("/css/**", "/images/**", "/js/**", "/api/footer", "/api/header" ).permitAll()
            //If it is a get request to the api we will let everyone view the item(view product in store)
            .antMatchers(HttpMethod.GET, "/api/products", "api/images").permitAll();

    //We want everyone authenticated
    http.authorizeRequests().anyRequest().authenticated();

    //Adding a filter.
    http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), jwtService));

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

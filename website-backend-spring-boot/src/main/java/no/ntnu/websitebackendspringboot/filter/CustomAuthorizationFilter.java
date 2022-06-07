package no.ntnu.websitebackendspringboot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.websitebackendspringboot.services.JwtService;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 *
 * This class will check if the token provide from the user is correct.
 * The OncePerRequestFilter is going to intercept every request that comes into the application.
 */
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  private final JwtService jwtService;


  public CustomAuthorizationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  private String readAccessCookie(HttpServletRequest request) {


    Cookie[] cookies = request.getCookies();

    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase("access_Token")) {
        return "Bearer " + cookie.getValue();
      }
    }
    return null;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    //These are the request that we are not going to check the tokens on.
    if (request.getServletPath().equals("/login") ||
        request.getServletPath().equals("/api/token/refresh")) {
      //we are just passing it along, as if the user has valid permissions.
      filterChain.doFilter(request, response);
    } else {

      //We only retrieve the date from the header with the type authorization
      //so if this is null then there was non information in the request's authorization header

      //Right now we are using cookie
      String authorizationToken = readAccessCookie(request);

      log.info("Discovered token: " + authorizationToken);

      //This if statement will be true if the request is of the type authorization header
      // && it starts with "Bearer ".
      if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {

        try {
          Collection<SimpleGrantedAuthority> authorities =
              jwtService.extractClaim(authorizationToken);

          // This is how we tell spring that this is a user
          // here we have their username and their roles and that tells spring what they can to in the application
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  jwtService.extractUsername(authorizationToken), null, authorities);

          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          // Here spring is going to look at the user, look at their role, and determine what resource they can access
          // and what they can access, depending on the roles.
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          //Going to call the filer chain, because we still need to let the request continued its course
          filterChain.doFilter(request, response);

        } catch (Exception exception) {

          //token is not valid.
          //Not able to verify token.
          //token is expired.

          log.error("Error logging in: {}", exception.getMessage());
          response.setStatus(FORBIDDEN.value());
          response.sendRedirect(request.getContextPath() + "/access-denied");


        }

      } else {
        //so if the request do not have an authorization header
        //we are just going to let the request continue.
        filterChain.doFilter(request, response);
      }
    }
  }
}



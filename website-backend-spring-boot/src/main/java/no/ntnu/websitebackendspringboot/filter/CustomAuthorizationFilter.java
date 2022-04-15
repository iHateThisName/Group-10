package no.ntnu.websitebackendspringboot.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.ntnu.websitebackendspringboot.services.JwtService;
import no.ntnu.websitebackendspringboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
  @Autowired
  private UserService userService;


  public CustomAuthorizationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

//
//    final String authorizationHeader = request.getHeader("Authorization");
//    String username = null;
//    String jwt = null;
//
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//      username = jwtService.extractUsername(authorizationHeader);
//    }
//
//    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//      log.info("Trying to validate the token for the user {}", username);
//      UserDetails userDetails = userService.loadUserByUsername(username);
//
//      if (jwtService.validateToken(jwt, userDetails)) {
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//            userDetails, null, userDetails.getAuthorities());
//
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//      }
//    }
//    filterChain.doFilter(request, response);
//  }


    //These are the request that we are not going to check the tokens on.
    if (request.getServletPath().equals("/login") ||
        request.getServletPath().equals("/api/token/refresh")) {
      //we are just passing it along, as if the user has valid permissions.
      filterChain.doFilter(request, response);
    } else {


      //We only retrieve the date from the header with the type authorization
      //so if this is null then there was non information in the request's authorization header
      String authorizationHeader = request.getHeader(AUTHORIZATION);

      //This if statement will be true if the request is of the type authorization header
      // && it starts with "Bearer ".
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

        try {


          Collection<SimpleGrantedAuthority> authorities =
              jwtService.extractClaim(authorizationHeader);

          // This is how we tell spring that this is a user
          // here we have their username and their roles and that tells spring what they can to in the application
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  jwtService.extractUsername(authorizationHeader),
                  null, authorities);

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
          response.setHeader("error ", exception.getMessage());
          response.setStatus(FORBIDDEN.value());

          //Creating own custom error message
          Map<String, String> error = new HashMap<>();
          error.put("errorMessage", exception.getMessage());
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), error);

        }

      } else {
        //so if the request do not have an authorization header
        //we are just going to let the request continue.
        filterChain.doFilter(request, response);
      }
    }
  }
}



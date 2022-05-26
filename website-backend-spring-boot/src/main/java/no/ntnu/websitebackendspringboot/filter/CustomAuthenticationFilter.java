package no.ntnu.websitebackendspringboot.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import no.ntnu.websitebackendspringboot.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  //used to authenticate the user.
  private final AuthenticationManager authenticationManager;
  //logger
  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  private final JwtService jwtService;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                    JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }



  /**
   * user try to authenticate
   * This methode is going to be called because we are going to try to attempt to authenticate.
   * if the authentication is not successful, spring is just going to spit out an error to the user
   * But if the authentication is successful, then it's going to call the successfulAuthentication methode.
   *
   * @throws AuthenticationException throws an error when the authentication was not successful.
   */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    if (request.getServletPath().equals("/login")) {

      String username = request.getParameter("username");
      String password = request.getParameter("password");
      log.info("Username is: {}", username);
      log.info("Password is: {}", password);

      if (username == null) {
        return null;
      } else {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username, password);

        try {
          return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException authenticationException) {
          log.info(authenticationException.getMessage());
        }
      }
    }

    log.warn("Return null in attemptAuthentication");
    return null;

  }

  /**
   * This methode will send the access token and the refresh token.
   * Whenever the login is successful.
   * The methode is called when the login is successful.
   *
   * @param request
   * @param response
   * @param chain
   * @param authentication
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authentication) throws IOException, ServletException {


    //This user is from org.springframework.security.core.userdetails.User
    User user = (User) authentication.getPrincipal();

    //here we are going to generate the token using the jwtService
    String accessToken = jwtService.generateAccessToken(user, request);
    String refreshToken = jwtService.generateRefreshToken(user, request);

    //This is a way to use ResponseEntity to set a header
    HttpHeaders headers = new HttpHeaders();
    headers.add("access_Token", accessToken);
    headers.add("refresh_Token",refreshToken);


    //This will make it stay in the response header
    response.addHeader("access_Token", accessToken);
    response.addHeader("refresh_Token", refreshToken);

    //cookie
    Cookie cookie = new Cookie("access_Token", accessToken);
    response.addCookie(cookie);

//    response.sendRedirect("/home");





    //Cookie
    Cookie cookieJwt = new Cookie("access_Token", accessToken);
    Cookie cookieRefreshJwt = new Cookie("refresh_Token", accessToken);

    cookieJwt.setMaxAge(60*30);

    log.info("Adding AccessCookie");

    response.addCookie(cookieJwt);

    response.sendRedirect("/home");



    //this will just give the ResponseEntity as a json to the user when successfully to login
//    new ObjectMapper().writeValue(response.getOutputStream(), ResponseEntity.ok().headers(headers).body("👍"));

  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException failed) throws IOException, ServletException {

    log.info("failed authentication while attempting to access "
            + request.getServletPath());

    //Adding more descriptive message
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
  }
}

package no.ntnu.websitebackendspringboot.filter;

import no.ntnu.websitebackendspringboot.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 *
 * This class handles the log in authentication on the website.
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
   * if the authentication is not successful, because of "the user was not found" we will call unsuccessfulAuthentication
   * But if the authentication is successful, then it's going to call the successfulAuthentication methode.
   *
   * @throws AuthenticationException throws an error when the authentication was not successful.
   */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {


      String username = request.getParameter("username");
      String password = request.getParameter("password");
      log.info("Username is: {}", username);

      if (username != null) {

        try {

          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
          return authenticationManager.authenticate(authenticationToken);

        } catch (InternalAuthenticationServiceException exception) {
          //user tried to log in with wrong authentication
          log.warn("A user tried to log in with the wrong authentication");
          try {
            unsuccessfulAuthentication(request, response, exception);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
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

    //Cookie
    Cookie cookieJwt = new Cookie("access_Token", accessToken);

    cookieJwt.setMaxAge(60*30);

    log.info("Adding AccessCookie");

    response.addCookie(cookieJwt);

    response.sendRedirect("/home");

  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException failed) throws IOException, ServletException {

    log.info("failed authentication while attempting to access "
            + request.getServletPath());

    if (!request.getMethod().equals("Get")) {
      log.info("User get sent UNAUTHORIZED message");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write("User dose not exist");

    }

  }
}

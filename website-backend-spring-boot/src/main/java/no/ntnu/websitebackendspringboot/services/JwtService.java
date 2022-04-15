package no.ntnu.websitebackendspringboot.services;

import static java.util.Arrays.stream;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
@Component
public class JwtService {

  @Value(value = "${jwt.secret.key}")
  private String jwtKey;



  public String generateAccessToken(User user, HttpServletRequest request) {
    Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes());
    return JWT.create()
        .withSubject(user.getUsername())
        .withClaim("roles", user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .withIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
        //expire date for the access token.
        .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusWeeks(1)))
        //the issuer is the name of the author of this token
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
  }
  public String generateRefreshToken(UserDetails userDetails, HttpServletRequest request) {
    Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes());
    return JWT.create()
        .withSubject(userDetails.getUsername())
        .withIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
        //expire date for the access token.
        .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
        //the issuer is the name of the author of this token
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
  }

  private DecodedJWT getDecodedJWT(String token) {

    //Remove "Bearer "
    if (token.startsWith("Bearer")) {
      token = token.substring("Bearer ".length());
    }

    //Need the algorithm to read the token.
    Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes());

    //need a verifier to verify and decode the token
    JWTVerifier verifier = JWT.require(algorithm).build();

    //decoding the token to make it readable as well as verifying it.
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  /**
   * Find username from a JWT token
   *
   * @param token JWT token
   * @return Username
   */
  public String extractUsername(String token) {
    return getDecodedJWT(token).getSubject();
  }

  public Collection<SimpleGrantedAuthority> extractClaim(String token) {
    //extracting the roles
    String[] roles = getDecodedJWT(token).getClaim("roles").asArray(String.class);
    //Need to convert the string roles from the token to a SimpleGrantedAuthority
    //and have that in a Collection/List
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    stream(roles).forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role));
    });
    return authorities;
  }

  private Boolean isTokenExpired(String token) {
    return getDecodedJWT(token)
        .getExpiresAt().before(new Date());
  }

  /**
   * Check if a token is valid for a given user
   *
   * @param token       Token to validate
   * @param userDetails Object containing user details
   * @return True if the token matches the current user and is still valid
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

}

package org.nbc.account.trollo.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  // Header KEY value
  public static final String AUTHORIZATION_HEADER = "Authorization";

  // Token identifier
  public static final String BEARER_PREFIX = "Bearer ";

  @Value("${jwt.secret.key}") //SecretKey encoded with Base64
  private String secretKey;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  private Key key;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.error("Invalid JWT signature");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty");
    }
    return false;
  }

  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String createToken(String email) {
    Date date = new Date();

    // token will be expired in 60 mins
    long TOKEN_TIME = 60 * 60 * 1000;
    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(date.getTime() + TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  // save at JWT Cookie
  public void addJwtToCookie(String token, HttpServletResponse res) {
    try {
      token = URLEncoder.encode(token, "UTF-8").replaceAll("\\+", "%20"); // encode bc there has to be no space in Cookie Value

      Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
      cookie.setPath("/");

      // add Cookie to Response
      res.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage());
    }
  }

  // get Jwt(Cookie value) from HttpServletRequest
  public String getTokenFromRequest(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if(cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
          try {
            return URLDecoder.decode(cookie.getValue().replaceAll("Bearer%20", ""), "UTF-8"); // decode value
          } catch (UnsupportedEncodingException e) {
            return null;
          }
        }
      }
    }
    return null;
  }

}

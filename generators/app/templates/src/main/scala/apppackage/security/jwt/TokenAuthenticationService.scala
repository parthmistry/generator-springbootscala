package <%= PACKAGE %>.security.jwt

import java.util.Date
import javax.servlet.http.HttpServletRequest

import io.jsonwebtoken.{Jwts, SignatureAlgorithm}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class TokenAuthenticationService {

  private val log = LoggerFactory.getLogger(classOf[TokenAuthenticationService])

  @Value("${jwt.secretKey}")
  private var SECRET: String = _

  @Value("${jwt.expirationTime}")
  private var EXPIRATIONTIME: Long = _

  private val TOKEN_PREFIX = "Bearer"
  private val HEADER_STRING = "Authorization"
  private val AUTHORITIES_KEY = "auth"

  def addAuthentication(authentication: Authentication): String = {
    log.debug("inside addAuthentication()")
    Jwts.builder()
        .setSubject(authentication.getPrincipal.toString)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .claim(AUTHORITIES_KEY, authentication.getAuthorities.asScala.map(_.getAuthority).mkString(","))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact()
  }

  def getAuthentication(request: HttpServletRequest): Authentication = {
    log.debug("inside getAuthentication()")
    val token: String = request.getHeader(HEADER_STRING)
    if (Option(token).isDefined) {
      // parse the token.
      val parsedToken = Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody

      val user = parsedToken.getSubject
      val authorities = parsedToken.get(AUTHORITIES_KEY, classOf[String]).split(",").map(new SimpleGrantedAuthority(_)).toList.asJava

      if(Option(user).isDefined)
        return new UsernamePasswordAuthenticationToken(user, null, authorities)
    }
    null
  }
}

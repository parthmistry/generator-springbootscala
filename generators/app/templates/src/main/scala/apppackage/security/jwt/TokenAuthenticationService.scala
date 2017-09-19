package <%= PACKAGE %>.security.jwt

import java.util.Date
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import io.jsonwebtoken.{Jwts, SignatureAlgorithm}
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class TokenAuthenticationService {
  val EXPIRATIONTIME = 864000000 // 10 days
  val SECRET = "ThisIsASecret"
  val TOKEN_PREFIX = "Bearer"
  val HEADER_STRING = "Authorization"

  def addAuthentication(res: HttpServletResponse, username: String): Unit = {
    val JWT: String = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact()
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT)
  }

  def getAuthentication(request: HttpServletRequest): Authentication = {
    val token: String = request.getHeader(HEADER_STRING)
    if (Option(token).isDefined) {
      // parse the token.
      val user: String = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody
          .getSubject
      if(Option(user).isDefined)
          return new UsernamePasswordAuthenticationToken(user, null, List().asJava)
    }
    null
  }
}

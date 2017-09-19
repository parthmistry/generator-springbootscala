package <%= PACKAGE %>.security.jwt

import javax.servlet.FilterChain
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import scala.collection.JavaConverters._


class JWTLoginFilter (val url: String, val authManager: AuthenticationManager, tokenAuthenticationService: TokenAuthenticationService) extends AbstractAuthenticationProcessingFilter(new AntPathRequestMatcher(url)) {
  setAuthenticationManager(authManager)

  override def attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication = {
    val objectMapper = new ObjectMapper()
    val creds = new ObjectMapper().readTree(req.getInputStream)
    getAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.get("username").textValue(), creds.get("password").textValue(), List().asJava))
  }

  override protected def successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain, auth: Authentication): Unit = {
    tokenAuthenticationService.addAuthentication(res, auth.getName)
  }
}

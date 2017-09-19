package <%= PACKAGE %>.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JWTAuthenticationFilter(tokenAuthenticationService: TokenAuthenticationService) extends GenericFilterBean {

  override def doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain): Unit = {
    val authentication = tokenAuthenticationService.getAuthentication(request.asInstanceOf[HttpServletRequest])
    SecurityContextHolder.getContext.setAuthentication(authentication)
    filterChain.doFilter(request, response)
  }
}

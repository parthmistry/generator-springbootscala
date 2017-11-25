package <%= PACKAGE %>.web.rest

import javax.servlet.http.HttpServletRequest

import <%= PACKAGE %>.web.rest.vm.user.{LoginResponseVM, LoginVM}
import <%= PACKAGE %>.security.jwt.TokenAuthenticationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/api"))
class UsersController @Autowired() (private val authenticationManager: AuthenticationManager,
									private val tokenAuthenticationService: TokenAuthenticationService) {

  private val log = LoggerFactory.getLogger(classOf[UsersController])

  @GetMapping(Array("/login"))
  def isAuthenticated(request: HttpServletRequest): String = {
    log.debug("inside isAuthenticated()")
    request.getRemoteUser
  }

  @RequestMapping(Array("/login"))
  def login(@RequestBody loginRequest: LoginVM) : LoginResponseVM = {
    log.debug("inside login()")
    val auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
    LoginResponseVM(tokenAuthenticationService.addAuthentication(auth))
  }
}

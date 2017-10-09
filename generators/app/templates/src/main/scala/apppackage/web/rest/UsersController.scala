package <%= PACKAGE %>.web.rest

import <%= PACKAGE %>.web.rest.vm.user.{LoginResponseVM, LoginVM}
import <%= PACKAGE %>.security.jwt.TokenAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/api"))
class UsersController @Autowired() (private val authenticationManager: AuthenticationManager, private val tokenAuthenticationService: TokenAuthenticationService) {

  @RequestMapping(Array("/login"))
  def login(@RequestBody loginRequest: LoginVM) : LoginResponseVM = {
    val auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
    LoginResponseVM(tokenAuthenticationService.addAuthentication(loginRequest.username))
  }
}

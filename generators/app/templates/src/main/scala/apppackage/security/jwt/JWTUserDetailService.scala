package <%= PACKAGE %>.security.jwt

import java.nio.file.attribute.UserPrincipalNotFoundException

import <%= PACKAGE %>.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.security.core.userdetails.{User, UserDetails, UserDetailsService}

import collection.JavaConverters._

@Component
class JWTUserDetailService @Autowired() (private val userRepository: UserRepository) extends UserDetailsService {

  override def loadUserByUsername(username: String): UserDetails = {
    userRepository.findOneWithAuthoritiesByUsername(username).map(user =>
      new User(username, user.passwordHash, user.authorities.asScala.map(authority => new SimpleGrantedAuthority(authority.getName)).asJava))
      .getOrElse(throw new UserPrincipalNotFoundException("User not found."))
  }
}

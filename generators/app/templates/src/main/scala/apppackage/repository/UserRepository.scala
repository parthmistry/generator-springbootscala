package <%= PACKAGE %>.repository

import <%= PACKAGE %>.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

trait UserRepository extends CrudRepository[User, java.lang.Long] {

  @EntityGraph(attributePaths = Array("authorities"))
  def findOneWithAuthoritiesByUsername(username: String): Option[User]

}

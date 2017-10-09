package <%= PACKAGE %>.repository

import <%= PACKAGE %>.domain.Authority
import org.springframework.data.repository.CrudRepository

trait AuthorityRepository extends CrudRepository[Authority, String]

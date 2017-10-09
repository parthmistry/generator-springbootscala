package <%= PACKAGE %>.domain

import java.sql.Timestamp
import java.util
import javax.persistence._

import scala.beans.BeanProperty

@Entity(name = "users")
class User {
  @Id
  @BeanProperty
  @Column(name = "id")
  var id: java.lang.Long = _

  @BeanProperty
  @Column(name = "username")
  var username: String = _

  @BeanProperty
  @Column(name = "password_hash")
  var passwordHash: String = _

  @BeanProperty
  @Column(name = "first_name")
  var firstName: String = _

  @BeanProperty
  @Column(name = "last_name")
  var lastName: String = _

  @BeanProperty
  @Column(name = "created_date")
  var createdDate: Timestamp = _

  @BeanProperty
  @Column(name = "last_modified_date")
  var lastModifiedDate: Timestamp = _

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_authorities", joinColumns = Array(new JoinColumn(name = "user_id", referencedColumnName = "id")), inverseJoinColumns = Array(new JoinColumn(name = "authority_name", referencedColumnName = "name")))
  var authorities: util.Set[Authority] = new util.HashSet[Authority]

  override def toString = s"User(id=$id, username=$username, passwordHash=$passwordHash, firstName=$firstName, lastName=$lastName, createdDate=$createdDate, lastModifiedDate=$lastModifiedDate)"
}

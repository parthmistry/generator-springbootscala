package <%= PACKAGE %>.domain

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "authorities")
class Authority {
  @Id
  @BeanProperty
  @Column(name = "name")
  var name: String = _

  override def toString = s"Authority(name=$name)"
}

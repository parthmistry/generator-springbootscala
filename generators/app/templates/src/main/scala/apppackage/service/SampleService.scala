package <%= PACKAGE %>.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SampleService {

  private val log = LoggerFactory.getLogger(classOf[SampleService])

  def sayHello(name: String) : String = {
    log.trace("inside sayHello()")
    "Hello " + name
  }
}

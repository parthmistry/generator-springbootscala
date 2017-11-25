package <%= PACKAGE %>.web.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import <%= PACKAGE%>.security.SecurityConstants
import <%= PACKAGE%>.service.SampleService
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api"))
class SampleController @Autowired() (private val sampleService: SampleService) {

  private val log = LoggerFactory.getLogger(classOf[SampleController])

  @RequestMapping(Array("/sample"))<% if(AUTH === 'JWT') { %>
  @Secured(value = Array(SecurityConstants.ADMIN, SecurityConstants.USER))<% } %>
  def sample(@RequestParam name: String) : String = {
    log.debug("inside sample()")
    sampleService.sayHello(name)
  }
}

package <%= PACKAGE %>.web.rest

import org.springframework.beans.factory.annotation.Autowired
import <%= PACKAGE%>.service.SampleService
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

@RestController
class SampleController @Autowired() (private val sampleService: SampleService) {

  @RequestMapping(Array("/sample"))
  def sample(@RequestParam name: String) : String = {
    sampleService.sayHello(name)
  }
}

package <%= PACKAGE %>

import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@RunWith(value = classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[<%= CAPITALIZED_NAME %>Conf]), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SampleControllerTests {

  private val log = LoggerFactory.getLogger(classOf[SampleControllerTests])

  @Autowired var restTemplate: RestTemplate = _

  @Test
  def sampleTest(): Unit = {
    log.debug("inside sampleTest()")
    val name = "User"
    val response = restTemplate.getForObject(s"http://localhost:8080/api/sample?name=$name", classOf[String])
    Assert.assertEquals(s"Hello $name", response)
  }
}

package <%= PACKAGE %>

import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.{HttpEntity, HttpMethod}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(value = classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[<%= CAPITALIZED_NAME %>Conf]), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SampleControllerTests extends AbstractTests {

  private val log = LoggerFactory.getLogger(classOf[SampleControllerTests])

  @Test
  def sampleTest(): Unit = {
    log.debug("inside sampleTest()")
    val name = "User"
    val requestEntity = new HttpEntity[String](getHeadersWithAuth)
    val response = restTemplate.exchange(s"$API_BASE_URL/sample?name=$name", HttpMethod.GET, requestEntity, classOf[String]).getBody
    Assert.assertEquals(s"Hello $name", response)
  }
}

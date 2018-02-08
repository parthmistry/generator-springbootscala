package <%= PACKAGE %>

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpEntity, HttpHeaders, MediaType}
import org.springframework.util.{LinkedMultiValueMap, MultiValueMap}
import org.springframework.web.client.RestTemplate

abstract class AbstractTests {

  @Autowired protected var restTemplate: RestTemplate = _

  @Autowired protected var objectMapper: ObjectMapper = _

  protected val API_BASE_URL = "http://localhost:8080/api"

  def getHeaders = {
    val headers = new LinkedMultiValueMap[String, String]
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    headers
  }

}

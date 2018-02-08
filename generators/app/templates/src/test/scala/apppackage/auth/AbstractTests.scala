package <%= PACKAGE %>

import com.fasterxml.jackson.databind.ObjectMapper
import <%= PACKAGE %>.web.rest.vm.user.{LoginResponseVM, LoginVM}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpEntity, HttpHeaders, MediaType}
import org.springframework.util.{LinkedMultiValueMap, MultiValueMap}
import org.springframework.web.client.RestTemplate

abstract class AbstractTests {

  @Autowired protected var restTemplate: RestTemplate = _

  @Autowired protected var objectMapper: ObjectMapper = _

  protected val API_BASE_URL = "http://localhost:8080/api"

  protected var authToken: String = _

  def getAuthToken = {
    if(Option(authToken).isEmpty) {
      val httpEntity = new HttpEntity[String](objectMapper.writeValueAsString(LoginVM("admin", "admin")), getHeaders)
      val loginResponse = objectMapper.readValue[LoginResponseVM](restTemplate.postForObject(s"$API_BASE_URL/login", httpEntity, classOf[String]), classOf[LoginResponseVM])
      authToken = loginResponse.token
    }
    authToken
  }

  def getHeaders = {
    val headers = new LinkedMultiValueMap[String, String]
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    headers
  }

  def getHeadersWithAuth = {
    val headers = getHeaders
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAuthToken)
    headers
  }

}

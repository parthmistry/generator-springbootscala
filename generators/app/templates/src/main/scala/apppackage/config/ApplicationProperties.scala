package <%= PACKAGE %>.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration

import scala.beans.BeanProperty

@ConfigurationProperties(value = "application", ignoreUnknownFields = false)
class ApplicationProperties {

    @BeanProperty var cors: CorsConfiguration = _<%if (ELASTICSEARCH) { %>

    @BeanProperty var elasticsearch: ESProperties = _<% } %>

}
<%if (ELASTICSEARCH) { %>
class ESProperties {

    @BeanProperty
    var targetUri: String = _

}<% } %>
package <%= PACKAGE %>.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration

import scala.beans.BeanProperty

@ConfigurationProperties(value = "application", ignoreUnknownFields = false)
class ApplicationProperties {
    @BeanProperty var cors: CorsConfiguration = new CorsConfiguration
}

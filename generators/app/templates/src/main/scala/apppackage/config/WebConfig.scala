package <%= PACKAGE %>.config

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.{WebMvcConfigurationSupport, EnableWebMvc, WebMvcConfigurerAdapter}
import collection.JavaConverters._

@Configuration
class WebConfig @Autowired()(private val applicationProperties: ApplicationProperties) extends WebMvcConfigurerAdapter {
    override def configureMessageConverters(converters: java.util.List[HttpMessageConverter[_]]): Unit = {
        super.configureMessageConverters(converters)
        converters.add(jackson2HttpMessageConverter())
    }

    def jackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter(objectMapper())

    def objectMapper(): ObjectMapper =
        new ObjectMapper() {
            setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            registerModule(DefaultScalaModule)
        }

override def addCorsMappings(registry: CorsRegistry) = {
        registry.addMapping("/api/**")
          .allowedOrigins(applicationProperties.cors.getAllowedOrigins.asScala:_*)
          .allowedMethods(applicationProperties.cors.getAllowedMethods.asScala:_*)
          .allowedHeaders(applicationProperties.cors.getAllowedHeaders.asScala:_*)
          .exposedHeaders(applicationProperties.cors.getExposedHeaders.asScala:_*)
          .allowCredentials(true).maxAge(1800)
    }
}

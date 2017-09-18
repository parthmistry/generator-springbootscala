package <%= PACKAGE %>

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.{WebMvcConfigurationSupport, EnableWebMvc, WebMvcConfigurerAdapter}

@Configuration
class WebConfig extends WebMvcConfigurerAdapter {
    override def configureMessageConverters(converters: java.util.List[HttpMessageConverter[_]]): Unit = {
        super.configureMessageConverters(converters)
        converters.add(jackson2HttpMessageConverter())
    }

    @Bean
    def jackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter(objectMapper())

    @Bean
    def objectMapper(): ObjectMapper =
        new ObjectMapper() {
            setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            registerModule(DefaultScalaModule)
        }

}

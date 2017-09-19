package <%= PACKAGE %>

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.context.properties.EnableConfigurationProperties
import <%= PACKAGE %>.config.ApplicationProperties

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(Array(classOf[ApplicationProperties]))
class <%= CAPITALIZED_NAME %>Conf {

}

object <%= CAPITALIZED_NAME %>Application extends App {
	SpringApplication.run(classOf[<%= CAPITALIZED_NAME %>Conf])
}
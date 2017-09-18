package <%= PACKAGE %>

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}

@SpringBootApplication
@EnableAutoConfiguration
class <%= CAPITALIZED_NAME %>Conf {

}

object <%= CAPITALIZED_NAME %>Application extends App {
	SpringApplication.run(classOf[<%= CAPITALIZED_NAME %>Conf])
}
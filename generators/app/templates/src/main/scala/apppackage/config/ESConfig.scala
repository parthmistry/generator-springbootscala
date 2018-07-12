package <%= PACKAGE %>.config

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import org.elasticsearch.common.settings.Settings
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ESConfig @Autowired()(private val applicationProperties: ApplicationProperties) {

    private val log = LoggerFactory.getLogger(classOf[ESConfig])

    @Bean
    def client(): HttpClient = {
        HttpClient(ElasticsearchClientUri(applicationProperties.elasticsearch.targetUri))
    }

}

package <%= PACKAGE %>.config

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import org.elasticsearch.common.settings.Settings
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ESConfig @Autowired()(private val applicationProperties: ApplicationProperties) {

    private val log = LoggerFactory.getLogger(classOf[ESConfig])

    @Bean
    def client(): TcpClient = {
        val settings = Settings.builder().put("cluster.name", applicationProperties.elasticsearch.clusterName).build()
        TcpClient.transport(settings, ElasticsearchClientUri(applicationProperties.elasticsearch.targetUri))
    }

}

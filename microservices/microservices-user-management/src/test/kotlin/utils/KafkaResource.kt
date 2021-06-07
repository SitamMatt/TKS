package utils

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.net.URI
import java.net.URL
import java.util.*

class KafkaResource : QuarkusTestResourceLifecycleManager{

    override fun start(): MutableMap<String, String> {
        kafka.start()
        val url = URI(kafka.bootstrapServers)
        val x = "${url.host}:${url.port}"
        return Collections.singletonMap("kafka.bootstrap.servers", x)
    }

    override fun stop() {
        Thread.sleep(5000)
        kafka.close()
    }

    companion object{
        val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
    }
}
package microservices.user.management.webservices

import com.google.common.collect.ImmutableMap
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.KafkaResource
import utils.PostgresResource
import utils.WithKafka
import webservices.client.UserDto
import webservices.client.UserServiceService
import java.net.URL
import java.time.Duration
import java.util.*


@QuarkusTest
@QuarkusTestResource(PostgresResource::class)
@WithKafka
internal class UserServiceImplTest{

    @Test
    fun queryTest(){
        val service  = UserServiceService(URL("http://localhost:9090/soap/userservice?wsdl"))
        val port = service.userServicePort
        val response = port.getUser("mszewc@edu.pl")
        println(response)
    }

    @Test
    fun createTest(){
        val consumer: KafkaConsumer<String, String> = KafkaConsumer(
            ImmutableMap.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaResource.x,
                ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
            ) as Map<String, Any>?,
            StringDeserializer(),
            StringDeserializer()
        )
        consumer.subscribe(Collections.singletonList("clients"))
        val service  = UserServiceService(URL("http://localhost:9090/soap/userservice?wsdl"))
        val port = service.userServicePort
        val dto = UserDto()
        dto.email = "mzab@edu.pl"
        dto.isActive = true
        dto.password = "####"
        dto.role = "ADMIN"
        val response = port.registerUser(dto)
        println(response)
        Thread.sleep(5000)
        val records = consumer.poll(Duration.ofMillis(2000))
        records.count() shouldBeGreaterThan 0
    }
}
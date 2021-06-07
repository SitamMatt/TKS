package utils

import io.quarkus.test.common.QuarkusTestResource

@QuarkusTestResource(KafkaResource::class)
annotation class WithKafka()
